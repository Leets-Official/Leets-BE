package land.leets.domain.user.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import land.leets.domain.application.presentation.dto.ApplicationRequest
import land.leets.domain.application.type.Position
import land.leets.domain.application.type.SubmitStatus
import land.leets.domain.user.domain.User
import land.leets.domain.user.domain.repository.UserRepository
import land.leets.domain.user.exception.UserNotFoundException
import java.util.Optional
import java.util.UUID

class UpdateUserImplTest : DescribeSpec({

    val userRepository = mockk<UserRepository>()
    val updateUser = UpdateUserImpl(userRepository)

    describe("UpdateUserImpl") {
        context("유저 정보를 업데이트할 때") {
            val uid = UUID.randomUUID()
            val request = ApplicationRequest(
                name = "Test User",
                sid = "20201234",
                phone = "010-1234-5678",
                major = "CS",
                grade = "4",
                project = "Project",
                algorithm = "Algorithm",
                portfolio = "Portfolio",
                position = Position.DEV,
                career = "Career",
                interviewDay = "Monday",
                interviewTime = "10:00",
                motive = "Motive",
                expectation = "Expectation",
                capability = "Capability",
                conflict = "Conflict",
                passion = "Passion",
                submitStatus = SubmitStatus.SAVE
            )

            it("유저가 존재하면 정보를 업데이트한다") {
                val user = User(
                    sid = null,
                    name = "Test User",
                    phone = null,
                    email = "user@test.com",
                    sub = "google-oauth2|123456",
                    uid = uid
                )
                every { userRepository.findById(uid) } returns Optional.of(user)
                every { userRepository.save(any()) } returnsArgument 0

                val result = updateUser.execute(uid, request)

                verify { userRepository.save(user) }
                result.sid shouldBe "20201234"
                result.phone shouldBe "010-1234-5678"
            }

            it("유저를 찾을 수 없으면 UserNotFoundException을 던진다") {
                every { userRepository.findById(uid) } returns Optional.empty()

                shouldThrow<UserNotFoundException> {
                    updateUser.execute(uid, request)
                }
            }
        }
    }
})
