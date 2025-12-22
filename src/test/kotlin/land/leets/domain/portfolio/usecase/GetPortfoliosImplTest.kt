package land.leets.domain.portfolio.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import land.leets.domain.contributor.domain.Contributor
import land.leets.domain.contributor.domain.Position
import land.leets.domain.portfolio.domain.Portfolio
import land.leets.domain.portfolio.domain.repository.PortfolioRepository
import land.leets.domain.portfolio.exception.PortfolioNotFoundException
import land.leets.domain.portfolio.type.ProjectScope
import land.leets.domain.portfolio.type.ProjectType
import java.time.LocalDate
import java.util.*

class GetPortfoliosImplTest : DescribeSpec({
    val portfolioRepository = mockk<PortfolioRepository>()
    val getPortfolios = GetPortfoliosImpl(portfolioRepository)

    describe("GetPortfoliosImpl 유스케이스는") {

        context("generation이 null이면") {
            val finalPortfolio = createMockPortfolio(1L, "Final Project", ProjectScope.FINAL, 5L)
            val toyPortfolio = createMockPortfolio(2L, "Toy Project", ProjectScope.TOY, 5L)

            every { portfolioRepository.findAllByScopeOrderByGenerationDesc(ProjectScope.FINAL) } returns listOf(finalPortfolio)
            every { portfolioRepository.findAllByScopeOrderByGenerationDesc(ProjectScope.TOY) } returns listOf(toyPortfolio)

            it("모든 기수의 FINAL, TOY 프로젝트를 각각 조회해야 한다") {
                val response = getPortfolios.all(null)

                response.size shouldBe 2
                response[0].size shouldBe 1  // FINAL projects
                response[0][0].name shouldBe "Final Project"
                response[1].size shouldBe 1  // TOY projects
                response[1][0].name shouldBe "Toy Project"
            }
        }

        context("generation이 주어지면") {
            val generation = "5"
            val finalPortfolio = createMockPortfolio(1L, "5th Final", ProjectScope.FINAL, 5L)
            val toyPortfolio = createMockPortfolio(2L, "5th Toy", ProjectScope.TOY, 5L)

            every { portfolioRepository.findAllByGenerationAndScope(5L, ProjectScope.FINAL) } returns listOf(finalPortfolio)
            every { portfolioRepository.findAllByGenerationAndScope(5L, ProjectScope.TOY) } returns listOf(toyPortfolio)

            it("해당 기수의 FINAL, TOY 프로젝트를 각각 조회해야 한다") {
                val response = getPortfolios.all(generation)

                response.size shouldBe 2
                response[0].size shouldBe 1
                response[0][0].name shouldBe "5th Final"
                response[1].size shouldBe 1
                response[1][0].name shouldBe "5th Toy"
            }
        }

        context("portfolioId로 단일 포트폴리오를 조회할 때") {
            val portfolioId = 1L
            val portfolio = createMockPortfolio(portfolioId, "Test Project", ProjectScope.FINAL, 5L)

            every { portfolioRepository.findById(portfolioId) } returns Optional.of(portfolio)

            it("해당 포트폴리오의 상세 정보를 반환해야 한다") {
                val response = getPortfolios.one(portfolioId)

                response.portfolioId shouldBe portfolioId
                response.name shouldBe "Test Project"
                response.generation shouldBe 5L
                response.scope shouldBe ProjectScope.FINAL
            }
        }

        context("포트폴리오가 contributors를 포함할 때") {
            val portfolioId = 1L
            val contributor1 = Contributor(
                name = "이근표",
                position = Position.BACK_END,
                githubUrl = "https://github.com/rooTiket",
                profileUrl = "https://example.com/profile1.jpg",
                profile = "Backend Developer"
            )
            val contributor2 = Contributor(
                name = "홍길동",
                position = Position.FRONT_END,
                githubUrl = "https://github.com/hong",
                profileUrl = "https://example.com/profile2.jpg",
                profile = "Frontend Developer"
            )

            val portfolio = createMockPortfolio(portfolioId, "Team Project", ProjectScope.FINAL, 5L)
            portfolio.contributors.add(contributor1)
            portfolio.contributors.add(contributor2)

            every { portfolioRepository.findById(portfolioId) } returns Optional.of(portfolio)

            it("contributors를 포함한 상세 정보를 반환해야 한다") {
                val response = getPortfolios.one(portfolioId)

                response.portfolioId shouldBe portfolioId
                response.name shouldBe "Team Project"
                response.contributors.size shouldBe 2
                response.contributors[0].name shouldBe "이근표"
                response.contributors[0].position shouldBe Position.BACK_END
                response.contributors[1].name shouldBe "홍길동"
                response.contributors[1].position shouldBe Position.FRONT_END
            }
        }

        context("존재하지 않는 portfolioId로 조회하면") {
            val portfolioId = 999L

            every { portfolioRepository.findById(portfolioId) } returns Optional.empty()

            it("PortfolioNotFoundException을 던져야 한다") {
                shouldThrow<PortfolioNotFoundException> {
                    getPortfolios.one(portfolioId)
                }
            }
        }
    }
})

private fun createMockPortfolio(
    id: Long,
    name: String,
    scope: ProjectScope,
    generation: Long
): Portfolio {
    return Portfolio(
        generation = generation,
        name = name,
        summary = "Test summary",
        description = "Test description",
        type = ProjectType.WEB,
        scope = scope,
        startDate = LocalDate.of(2024, 1, 1),
        endDate = LocalDate.of(2024, 12, 31),
        serviceUrl = "https://test.com",
        logoImgName = "logo.png",
        mainImgName = "main.png",
        portfolioId = id
    )
}
