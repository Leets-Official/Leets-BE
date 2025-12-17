package land.leets.domain.interview.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import land.leets.domain.application.domain.Application
import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.domain.repository.InterviewRepository
import land.leets.domain.interview.type.HasInterview
import java.time.LocalDateTime

class GetInterviewImplTest : DescribeSpec({

    val interviewRepository = mockk<InterviewRepository>()
    val getInterview = GetInterviewImpl(interviewRepository)

    describe("GetInterviewImpl 유스케이스는") {
        context("면접 정보를 조회할 때") {
            val application = mockk<Application>()

            it("면접이 존재하면 면접 응답을 반환한다") {
                val fixedDate = LocalDateTime.now()
                val interview = Interview(
                    application = application,
                    hasInterview = HasInterview.CHECK,
                    fixedInterviewDate = fixedDate,
                    place = "Room A"
                )

                every { interviewRepository.findByApplication(application) } returns interview

                val result = getInterview.execute(application)

                result.hasInterview shouldBe HasInterview.CHECK
                result.fixedInterviewDate shouldBe fixedDate
                verify { interviewRepository.findByApplication(application) }
            }

            it("면접이 존재하지 않으면 빈 면접 응답을 반환한다") {
                every { interviewRepository.findByApplication(application) } returns null

                val result = getInterview.execute(application)

                result.hasInterview shouldBe null
                result.fixedInterviewDate shouldBe null
                verify { interviewRepository.findByApplication(application) }
            }
        }
    }
})