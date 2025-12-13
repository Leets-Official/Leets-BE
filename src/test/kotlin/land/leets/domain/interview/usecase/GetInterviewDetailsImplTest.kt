package land.leets.domain.interview.usecase

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import land.leets.domain.application.domain.Application
import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.domain.repository.InterviewRepository
import land.leets.domain.interview.type.HasInterview
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime
import java.util.Optional

@ExtendWith(MockKExtension::class)
class GetInterviewDetailsImplTest {

    @MockK
    private lateinit var interviewRepository: InterviewRepository

    private lateinit var getInterviewDetails: GetInterviewDetails

    @BeforeEach
    fun setUp() {
        getInterviewDetails = GetInterviewDetailsImpl(interviewRepository)
    }

    @Test
    fun `should get interview details when interview exists`() {
        // given
        val application = mockk<Application>()
        val fixedDate = LocalDateTime.now()
        val interview = Interview(
            id = 1L,
            application = application,
            hasInterview = HasInterview.CHECK,
            fixedInterviewDate = fixedDate,
            place = "Room A"
        )

        every { interviewRepository.findByApplication(application) } returns Optional.of(interview)

        // when
        val result = getInterviewDetails.execute(application)

        // then
        assertThat(result.id).isEqualTo(1L)
        assertThat(result.hasInterview).isEqualTo(HasInterview.CHECK)
        assertThat(result.fixedInterviewDate).isEqualTo(fixedDate)
        assertThat(result.place).isEqualTo("Room A")
        verify { interviewRepository.findByApplication(application) }
    }

    @Test
    fun `should get empty interview details when interview does not exist`() {
        // given
        val application = mockk<Application>()

        every { interviewRepository.findByApplication(application) } returns Optional.empty()

        // when
        val result = getInterviewDetails.execute(application)

        // then
        assertThat(result.id).isNull()
        assertThat(result.hasInterview).isNull()
        assertThat(result.fixedInterviewDate).isNull()
        assertThat(result.place).isNull()
        verify { interviewRepository.findByApplication(application) }
    }
}
