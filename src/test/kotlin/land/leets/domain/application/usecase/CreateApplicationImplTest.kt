package land.leets.domain.application.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.exception.ApplicationAlreadyExistsException
import land.leets.domain.application.presentation.dto.ApplicationRequest
import land.leets.domain.application.type.Position
import land.leets.domain.application.type.SubmitStatus
import land.leets.domain.auth.AuthDetails
import land.leets.domain.user.domain.User
import land.leets.domain.user.domain.repository.UserRepository
import java.util.*

class CreateApplicationImplTest : DescribeSpec({

    val applicationRepository = mockk<ApplicationRepository>()
    val userRepository = mockk<UserRepository>()
    val createApplication = CreateApplicationImpl(applicationRepository, userRepository)

    describe("CreateApplicationImpl 유스케이스는") {
        context("지원서 생성을 요청할 때") {
            val uid = UUID.randomUUID()
            val authDetails = mockk<AuthDetails> {
                every { getUid() } returns uid
            }
            val request = ApplicationRequest(
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
                passion = "Passion",
                submitStatus = SubmitStatus.SAVE
            )
            val user = User(
                sid = "20202020",
                name = "Test",
                phone = "010-1234-5678",
                email = "test@test.com",
                sub = "google-oauth2|123456",
                id = uid
            )

            it("지원서를 성공적으로 생성한다") {
                every { userRepository.findById(uid) } returns Optional.of(user)
                every { applicationRepository.findByUser_Uid(uid) } returns null
                every { userRepository.save(any()) } returns user
                every { applicationRepository.save(any()) } returnsArgument 0

                val result = createApplication.execute(authDetails, request)

                result.name shouldBe request.name
                verify { userRepository.save(user) }
                verify { applicationRepository.save(any()) }
            }

            it("이미 지원서가 존재하면 ApplicationAlreadyExistsException을 던진다") {
                val application = mockk<Application>()

                every { userRepository.findById(uid) } returns Optional.of(user)
                every { applicationRepository.findByUser_Uid(uid) } returns application

                shouldThrow<ApplicationAlreadyExistsException> {
                    createApplication.execute(authDetails, request)
                }
            }
        }
    }
})