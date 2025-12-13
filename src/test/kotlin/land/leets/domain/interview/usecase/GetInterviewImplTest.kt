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
class GetInterviewImplTest {

    @MockK
    private lateinit var interviewRepository: InterviewRepository

    private lateinit var getInterview: GetInterview

    @BeforeEach
    fun setUp() {
        getInterview = GetInterviewImpl(interviewRepository)
    }

    @Test
    fun `should get interview response when interview exists`() {
        // given
        val application = mockk<Application>()
        val fixedDate = LocalDateTime.now()
        val interview = Interview(
            application = application,
            hasInterview = HasInterview.CHECK,
            fixedInterviewDate = fixedDate,
            place = "Room A"
        )

        every { interviewRepository.findByApplication(application) } returns interview

        // when
        val result = getInterview.execute(application)

        // then
        assertThat(result.hasInterview).isEqualTo(HasInterview.CHECK)
        assertThat(result.fixedInterviewDate).isEqualTo(fixedDate)
        verify { interviewRepository.findByApplication(application) }
    }

    @Test
    fun `should get empty interview response when interview does not exist`() {
        // given
        val application = mockk<Application>()

        every { interviewRepository.findByApplication(application) } returns null

        // when
        val result = getInterview.execute(application)

        // then
        assertThat(result.hasInterview).isNull()
        assertThat(result.fixedInterviewDate).isNull()
        verify { interviewRepository.findByApplication(application) }
    }
}
