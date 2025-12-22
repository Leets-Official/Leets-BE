package land.leets.domain.user.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.type.SubmitStatus
import land.leets.domain.auth.AuthDetails
import land.leets.domain.shared.AuthRole
import land.leets.domain.user.domain.User
import land.leets.domain.user.domain.repository.UserRepository
import land.leets.domain.user.exception.UserNotFoundException
import java.util.Optional
import java.util.UUID

class GetUserDetailsImplTest : DescribeSpec({

    val userRepository = mockk<UserRepository>()
    val applicationRepository = mockk<ApplicationRepository>()
    val getUserDetails = GetUserDetailsImpl(userRepository, applicationRepository)

    describe("GetUserDetailsImpl") {
        context("유저 상세 정보를 조회할 때") {
            val uid = UUID.randomUUID()
            val authDetails = AuthDetails(uid, "user@test.com", AuthRole.ROLE_USER)
            val user = User(
                sid = "20201234",
                name = "Test User",
                phone = "010-1234-5678",
                email = "user@test.com",
                sub = "google-oauth2|123456",
                id = uid
            )

            it("유저가 존재하고 지원서가 없으면 NONE 상태로 반환한다") {
                every { userRepository.findById(uid) } returns Optional.of(user)
                every { applicationRepository.findByUser_Uid(uid) } returns null

                val response = getUserDetails.execute(authDetails)

                response.uid shouldBe uid
                response.name shouldBe "Test User"
                response.submitStatus shouldBe SubmitStatus.NONE
            }

            it("유저가 존재하고 지원서가 있으면 지원서 상태를 반환한다") {
                val application = mockk<Application> {
                    every { submitStatus } returns SubmitStatus.SUBMIT
                }
                every { userRepository.findById(uid) } returns Optional.of(user)
                every { applicationRepository.findByUser_Uid(uid) } returns application

                val response = getUserDetails.execute(authDetails)

                response.submitStatus shouldBe SubmitStatus.SUBMIT
            }

            it("유저를 찾을 수 없으면 UserNotFoundException을 던진다") {
                every { userRepository.findById(uid) } returns Optional.empty()

                shouldThrow<UserNotFoundException> {
                    getUserDetails.execute(authDetails)
                }
            }
        }
    }
})
