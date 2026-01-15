package land.leets.domain.temporaryApplication.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import land.leets.domain.application.type.Position
import land.leets.domain.auth.AuthDetails
import land.leets.domain.shared.AuthRole
import land.leets.domain.temporaryApplication.domain.TemporaryApplication
import land.leets.domain.temporaryApplication.domain.repository.TemporaryApplicationRepository
import land.leets.domain.temporaryApplication.presentation.dto.TemporaryApplicationRequest
import land.leets.domain.user.domain.User
import land.leets.domain.user.domain.repository.UserRepository
import land.leets.domain.user.exception.UserNotFoundException
import java.util.*

class SaveTemporaryApplicationImplTest : DescribeSpec({

    val temporaryApplicationRepository = mockk<TemporaryApplicationRepository>()
    val userRepository = mockk<UserRepository>()
    val saveTemporaryApplication = SaveTemporaryApplicationImpl(temporaryApplicationRepository, userRepository)

    describe("SaveTemporaryApplicationImpl") {
        context("지원서를 임시 저장할 때") {
            val uid = UUID.randomUUID()
            val authDetails = AuthDetails(uid, "test@test.com", AuthRole.ROLE_USER)
            val request = TemporaryApplicationRequest(
                name = "Test",
                sid = "20202020",
                phone = "010-1234-5678",
                major = "CS",
                grade = "4",
                project = "Project",
                algorithm = "Algo",
                portfolio = "Port",
                position = Position.DEV,
                career = "Career",
                interviewDay = "Monday",
                interviewTime = "10:00",
                motive = "Motive",
                expectation = "Expectation",
                capability = "Capability",
                conflict = "Conflict",
                passion = "Passion"
            )
            val user = User(
                sid = "20202020",
                name = "Test",
                phone = "010-1234-5678",
                email = "test@test.com",
                sub = "google-oauth2|123456",
                id = uid
            )

            it("기존 지원서가 없으면 새로 생성한다") {
                every { userRepository.findById(uid) } returns Optional.of(user)
                every { temporaryApplicationRepository.findByUser_Id(uid) } returns null
                every { temporaryApplicationRepository.save(any()) } returnsArgument 0
                every { userRepository.save(any()) } returns user

                val response = saveTemporaryApplication.execute(authDetails, request)

                response.name shouldBe request.name
                verify { temporaryApplicationRepository.save(any()) }
            }

            it("기존 지원서가 있으면 수정한다") {
                val existingApplication = mockk<TemporaryApplication>(relaxed = true)
                every { userRepository.findById(uid) } returns Optional.of(user)
                every { temporaryApplicationRepository.findByUser_Id(uid) } returns existingApplication
                every { temporaryApplicationRepository.save(any()) } returnsArgument 0
                every { userRepository.save(any()) } returns user
                every { existingApplication.name } returns "Updated Name"

                saveTemporaryApplication.execute(authDetails, request)

                verify { existingApplication.updateContent(request) }
                verify { temporaryApplicationRepository.save(existingApplication) }
            }

            it("전화번호가 있으면 유저 정보를 업데이트한다") {
                every { userRepository.findById(uid) } returns Optional.of(user)
                every { temporaryApplicationRepository.findByUser_Id(uid) } returns null
                every { temporaryApplicationRepository.save(any()) } returnsArgument 0
                every { userRepository.save(any()) } returns user

                saveTemporaryApplication.execute(authDetails, request)

                verify { userRepository.save(user) }
            }

            it("유저를 찾을 수 없으면 UserNotFoundException을 던진다") {
                every { userRepository.findById(uid) } returns Optional.empty()

                shouldThrow<UserNotFoundException> {
                    saveTemporaryApplication.execute(authDetails, request)
                }
            }
        }
    }
})
