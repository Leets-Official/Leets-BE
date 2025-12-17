package land.leets.domain.interview.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.domain.repository.InterviewRepository
import land.leets.domain.interview.presentation.dto.req.InterviewRequest
import java.time.LocalDateTime
import java.util.Optional

class CreateInterviewTest : DescribeSpec({

    val applicationRepository = mockk<ApplicationRepository>()
    val interviewRepository = mockk<InterviewRepository>()
    val createInterview = CreateInterviewImpl(applicationRepository, interviewRepository)

    describe("CreateInterviewImpl 유스케이스는") {
        context("면접 생성을 요청할 때") {
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

            it("면접을 성공적으로 생성한다") {
                every { applicationRepository.findById(1L) } returns Optional.of(application)
                every { interviewRepository.save(any()) } returns interview

                val result = createInterview.execute(request)

                result shouldBe interview
                verify { applicationRepository.findById(1L) }
                verify { interviewRepository.save(any()) }
            }
        }
    }
})