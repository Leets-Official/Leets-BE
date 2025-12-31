package land.leets.domain.application.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.exception.ApplicationNotFoundException
import land.leets.domain.application.type.ApplicationStatus
import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.domain.repository.InterviewRepository
import land.leets.domain.interview.type.HasInterview
import java.time.LocalDateTime
import java.util.UUID

class GetApplicationStatusImplTest : DescribeSpec({

    val applicationRepository = mockk<ApplicationRepository>()
    val interviewRepository = mockk<InterviewRepository>()
    val getApplicationStatus = GetApplicationStatusImpl(applicationRepository, interviewRepository)

    val uid = UUID.randomUUID()

    val interviewDate: LocalDateTime = LocalDateTime.of(2026, 3, 14, 14, 0)
    val interviewPlace = "전자정보도서관 1층 스터디룸 A"
    val applicationId = 1L

    fun mockApplication(
        status: ApplicationStatus,
    ): Application = mockk<Application>().also { application ->
        every { application.id } returns applicationId
        every { application.applicationStatus } returns status
    }

    fun mockInterview(
        hasInterview: HasInterview = HasInterview.PENDING,
    ): Interview = mockk<Interview>().also { interview ->
        every { interview.hasInterview } returns hasInterview
        every { interview.fixedInterviewDate } returns interviewDate
        every { interview.place } returns interviewPlace
    }

    describe("GetApplicationStatusImpl 유스케이스는") {

        context("지원서 상태 조회를 요청할 때") {

            it("지원서 상태가 PASS_PAPER이면 인터뷰 정보를 포함하여 반환한다") {
                val application = mockApplication(status = ApplicationStatus.PASS_PAPER)
                val interview = mockInterview()
                every { applicationRepository.findByUser_Id(uid) } returns application
                every { interviewRepository.findByApplication(application) } returns interview

                val result = getApplicationStatus.execute(uid)

                result.id shouldBe 1L
                result.status shouldBe ApplicationStatus.PASS_PAPER
                result.hasInterview shouldBe HasInterview.PENDING
                result.interviewDate shouldBe interviewDate
                result.interviewPlace shouldBe interviewPlace
            }

            it("PASS_PAPER이 아닌 상태들은 인터뷰 정보가 null이어야 한다") {
                val nonInterviewStatuses = listOf(
                    ApplicationStatus.PENDING,
                    ApplicationStatus.FAIL_PAPER,
                    ApplicationStatus.PASS,
                    ApplicationStatus.FAIL,
                )

                nonInterviewStatuses.forEach { status ->
                    val application = mockApplication(status = status)
                    val interview = mockInterview()
                    every { applicationRepository.findByUser_Id(uid) } returns application
                    every { interviewRepository.findByApplication(application) } returns interview

                    val result = getApplicationStatus.execute(uid)

                    result.id shouldBe 1L
                    result.status shouldBe status
                    result.hasInterview shouldBe null
                    result.interviewDate shouldBe null
                    result.interviewPlace shouldBe null
                }
            }

            it("지원서가 존재하지 않으면 ApplicationNotFoundException을 던진다") {
                every { applicationRepository.findByUser_Id(uid) } returns null

                shouldThrow<ApplicationNotFoundException> {
                    getApplicationStatus.execute(uid)
                }
            }
        }
    }
})
