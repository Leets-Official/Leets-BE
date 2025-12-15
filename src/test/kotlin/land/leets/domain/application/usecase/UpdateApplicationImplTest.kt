package land.leets.domain.application.usecase

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.presentation.dto.ApplicationRequest
import land.leets.domain.application.type.Position
import land.leets.domain.application.type.SubmitStatus
import land.leets.domain.auth.AuthDetails
import land.leets.domain.user.usecase.UpdateUser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class UpdateApplicationImplTest {

    @MockK
    private lateinit var applicationRepository: ApplicationRepository

    @MockK
    private lateinit var updateUser: UpdateUser

    private lateinit var updateApplication: UpdateApplication

    @BeforeEach
    fun setUp() {
        updateApplication = UpdateApplicationImpl(applicationRepository, updateUser)
    }

    @Test
    fun `should update application successfully`() {
        // given
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

        every { updateUser.execute(uid, request) } returns mockk()
        every { applicationRepository.findByUser_Uid(uid) } returns application
        every { applicationRepository.save(any()) } returnsArgument 0

        // when
        updateApplication.execute(authDetails, request)

        // then
        verify { updateUser.execute(uid, request) }
        verify { application.updateInfo(any()) }
        verify { applicationRepository.save(application) }
    }
}
