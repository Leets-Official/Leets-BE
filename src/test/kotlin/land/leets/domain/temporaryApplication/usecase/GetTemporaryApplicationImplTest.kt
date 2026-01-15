package land.leets.domain.temporaryApplication.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import land.leets.domain.auth.AuthDetails
import land.leets.domain.shared.AuthRole
import land.leets.domain.temporaryApplication.domain.TemporaryApplication
import land.leets.domain.temporaryApplication.domain.repository.TemporaryApplicationRepository
import java.util.*

class GetTemporaryApplicationImplTest : DescribeSpec({

    val temporaryApplicationRepository = mockk<TemporaryApplicationRepository>()
    val getTemporaryApplication = GetTemporaryApplicationImpl(temporaryApplicationRepository)

    describe("GetTemporaryApplicationImpl") {
        context("임시 저장된 지원서를 조회할 때") {
            val uid = UUID.randomUUID()
            val authDetails = AuthDetails(uid, "test@test.com", AuthRole.ROLE_USER)
            val temporaryApplication = mockk<TemporaryApplication>(relaxed = true)

            it("지원서가 존재하면 Response를 반환한다") {
                every { temporaryApplicationRepository.findByUser_Id(uid) } returns temporaryApplication
                every { temporaryApplication.name } returns "Test"

                val response = getTemporaryApplication.execute(authDetails)

                response?.name shouldBe "Test"
            }

            it("지원서가 존재하지 않으면 null을 반환한다") {
                every { temporaryApplicationRepository.findByUser_Id(uid) } returns null

                val response = getTemporaryApplication.execute(authDetails)

                response shouldBe null
            }
        }
    }
})
