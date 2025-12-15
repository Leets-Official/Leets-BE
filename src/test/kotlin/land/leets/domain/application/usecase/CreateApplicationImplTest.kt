package land.leets.domain.application.usecase

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
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
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import java.util.Optional

@ExtendWith(MockKExtension::class)
class CreateApplicationImplTest {

    @MockK
    private lateinit var applicationRepository: ApplicationRepository

    @MockK
    private lateinit var userRepository: UserRepository

    private lateinit var createApplication: CreateApplication

    @BeforeEach
    fun setUp() {
        createApplication = CreateApplicationImpl(applicationRepository, userRepository)
    }

    @Test
    fun `should create application successfully`() {
        // given
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
        val user = mockk<User>(relaxed = true) {
            every { getUid() } returns uid
        }

        every { userRepository.findById(uid) } returns Optional.of(user)
        every { applicationRepository.findByUser_Uid(uid) } returns null
        every { userRepository.save(any()) } returns user
        every { applicationRepository.save(any()) } returnsArgument 0

        // when
        val result = createApplication.execute(authDetails, request)

        // then
        assertThat(result.name).isEqualTo(request.name)
        verify { userRepository.save(user) }
        verify { applicationRepository.save(any()) }
    }

    @Test
    fun `should throw ApplicationAlreadyExistsException when application already exists`() {
        // given
        val uid = UUID.randomUUID()
        val authDetails = mockk<AuthDetails> {
            every { getUid() } returns uid
        }
        val request = mockk<ApplicationRequest>()
        val user = mockk<User> {
            every { getUid() } returns uid
        }
        val application = mockk<Application>()

        every { userRepository.findById(uid) } returns Optional.of(user)
        every { applicationRepository.findByUser_Uid(uid) } returns application

        // when & then
        assertThrows<ApplicationAlreadyExistsException> {
            createApplication.execute(authDetails, request)
        }
    }
}
