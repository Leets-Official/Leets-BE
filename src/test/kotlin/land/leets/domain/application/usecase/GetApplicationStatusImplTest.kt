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
import java.util.UUID

class GetApplicationStatusImplTest : DescribeSpec({

    val applicationRepository = mockk<ApplicationRepository>()
    val getApplicationStatus = GetApplicationStatusImpl(applicationRepository)

    val uid = UUID.randomUUID()

    val interviewDay = "2025년 3월 3일 (토)"
    val interviewTime = "15:00"

    fun mockApplication(
        id: Long = 1L,
        status: ApplicationStatus,
        day: String = interviewDay,
        time: String = interviewTime
    ): Application = mockk<Application>().also { application ->
        every { application.id } returns id
        every { application.applicationStatus } returns status
        every { application.interviewDay } returns day
        every { application.interviewTime } returns time
    }

    describe("GetApplicationStatusImpl 유스케이스는") {

        context("지원서 상태 조회를 요청할 때") {

            it("지원서 상태가 PASS_PAPER이면 인터뷰 정보를 포함하여 반환한다") {
                val application = mockApplication(status = ApplicationStatus.PASS_PAPER)
                every { applicationRepository.findByUser_Id(uid) } returns application

                val result = getApplicationStatus.execute(uid)

                result.id shouldBe 1L
                result.status shouldBe ApplicationStatus.PASS_PAPER
                result.interviewDay shouldBe interviewDay
                result.interviewTime shouldBe interviewTime
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
                    every { applicationRepository.findByUser_Id(uid) } returns application

                    val result = getApplicationStatus.execute(uid)

                    result.id shouldBe 1L
                    result.status shouldBe status
                    result.interviewDay shouldBe null
                    result.interviewTime shouldBe null
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
