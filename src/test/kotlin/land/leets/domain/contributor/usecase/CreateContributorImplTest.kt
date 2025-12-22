package land.leets.domain.contributor.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import land.leets.domain.contributor.domain.Contributor
import land.leets.domain.contributor.domain.Position
import land.leets.domain.contributor.domain.repository.ContributorRepository
import land.leets.domain.portfolio.domain.Portfolio

class CreateContributorImplTest : DescribeSpec({

    describe("CreateContributorImpl 유스케이스는") {
        context("기여자 목록과 포트폴리오가 주어지면") {
            val contributorRepository = mockk<ContributorRepository>()
            val createContributor = CreateContributorImpl(contributorRepository)
            val portfolio = mockk<Portfolio>(relaxed = true)
            val contributor1 = Contributor(
                name = "이근표",
                position = Position.BACK_END,
                githubUrl = "https://github.com/rooTiket",
                profileUrl = "https://example.com/profile.jpg",
                profile = "Backend Developer"
            )
            val contributor2 = Contributor(
                name = "조혜원",
                position = Position.FRONT_END,
                githubUrl = "https://github.com/cho",
                profileUrl = null,
                profile = null
            )
            val contributors = listOf(contributor1, contributor2)
            val capturedList = slot<List<Contributor>>()

            every { contributorRepository.saveAll(capture(capturedList)) } returnsArgument 0

            it("각 기여자에게 포트폴리오를 설정하고 저장해야 한다") {
                createContributor.execute(contributors, portfolio)

                verify(exactly = 1) { contributorRepository.saveAll(any<List<Contributor>>()) }
                capturedList.captured.size shouldBe 2
                capturedList.captured[0].portfolio shouldBe portfolio
                capturedList.captured[0].name shouldBe "이근표"
                capturedList.captured[1].portfolio shouldBe portfolio
                capturedList.captured[1].name shouldBe "조혜원"
            }
        }

        context("빈 기여자 목록이 주어지면") {
            val contributorRepository = mockk<ContributorRepository>()
            val createContributor = CreateContributorImpl(contributorRepository)
            val portfolio = mockk<Portfolio>(relaxed = true)
            val contributors = emptyList<Contributor>()

            every { contributorRepository.saveAll(any<List<Contributor>>()) } returnsArgument 0

            it("saveAll이 빈 리스트로 한 번 호출되어야 한다") {
                createContributor.execute(contributors, portfolio)

                verify(exactly = 1) { contributorRepository.saveAll(emptyList()) }
            }
        }
    }
})
