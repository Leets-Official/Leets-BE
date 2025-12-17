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

class GetInterviewDetailsImplTest : DescribeSpec({

    val interviewRepository = mockk<InterviewRepository>()
    val getInterviewDetails = GetInterviewDetailsImpl(interviewRepository)

    describe("GetInterviewDetailsImpl 유스케이스는") {
        context("면접 상세 정보를 조회할 때") {
            val application = mockk<Application>()

            it("면접이 존재하면 면접 상세 정보를 조회한다") {
                val fixedDate = LocalDateTime.now()
                val interview = Interview(
                    id = 1L,
                    application = application,
                    hasInterview = HasInterview.CHECK,
                    fixedInterviewDate = fixedDate,
                    place = "Room A"
                )

                every { interviewRepository.findByApplication(application) } returns interview

                val result = getInterviewDetails.execute(application)

                result.id shouldBe 1L
                result.hasInterview shouldBe HasInterview.CHECK
                result.fixedInterviewDate shouldBe fixedDate
                result.place shouldBe "Room A"
                verify { interviewRepository.findByApplication(application) }
            }

            it("면접이 존재하지 않으면 빈 면접 상세 정보를 조회한다") {
                every { interviewRepository.findByApplication(application) } returns null

                val result = getInterviewDetails.execute(application)

                result.id shouldBe null
                result.hasInterview shouldBe null
                result.fixedInterviewDate shouldBe null
                result.place shouldBe null
                verify { interviewRepository.findByApplication(application) }
            }
        }
    }
})