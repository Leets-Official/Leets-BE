package land.leets.domain.interview.usecase

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.domain.repository.InterviewRepository
import land.leets.domain.interview.presentation.dto.req.InterviewRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockKExtension::class)
class CreateInterviewImplTest {

    @MockK
    private lateinit var applicationRepository: ApplicationRepository

    @MockK
    private lateinit var interviewRepository: InterviewRepository

    private lateinit var createInterview: CreateInterview

    @BeforeEach
    fun setUp() {
        createInterview = CreateInterviewImpl(
            applicationRepository,
            interviewRepository
        )
    }

    @Test
    fun `should create interview successfully`() {
        // given
        val request = InterviewRequest(
            applicationId = 1L,
            fixedInterviewDate = LocalDateTime.now(),
            place = "전자정보도서관"
        )
        val application = mockk<Application>()
        val interview = Interview(
            application = application,
            fixedInterviewDate = request.fixedInterviewDate,
            place = request.place
        )

        every { applicationRepository.findById(1L) } returns Optional.of(application)
        every { interviewRepository.save(any()) } returns interview

        // when
        val result = createInterview.execute(request)

        // then
        assertThat(result).isEqualTo(interview)
        verify { applicationRepository.findById(1L) }
        verify { interviewRepository.save(any()) }
    }
}
