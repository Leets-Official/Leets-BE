package land.leets.domain.application.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.presentation.dto.ApplicationRequest
import land.leets.domain.application.type.Position
import land.leets.domain.application.type.SubmitStatus
import land.leets.domain.auth.AuthDetails
import land.leets.domain.user.usecase.UpdateUser
import java.util.*

class UpdateApplicationImplTest : DescribeSpec({

    val applicationRepository = mockk<ApplicationRepository>()
    val updateUser = mockk<UpdateUser>()
    val updateApplication = UpdateApplicationImpl(applicationRepository, updateUser)

    describe("UpdateApplicationImpl 유스케이스는") {
        context("지원서 수정을 요청할 때") {
            val uid = UUID.randomUUID()
            val authDetails = mockk<AuthDetails> {
                every { getUid() } returns uid
            }
            val request = ApplicationRequest(
                name = "Test Updated",
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
                submitStatus = SubmitStatus.SUBMIT
            )
            val application = mockk<Application>(relaxed = true)

            it("지원서를 성공적으로 수정한다") {
                every { updateUser.execute(uid, request) } returns mockk()
                every { applicationRepository.findByUser_Uid(uid) } returns application
                every { applicationRepository.save(any()) } returnsArgument 0

                updateApplication.execute(authDetails, request)

                verify { updateUser.execute(uid, request) }
                verify { application.updateInfo(any()) }
                verify { applicationRepository.save(application) }
            }
        }
    }
})