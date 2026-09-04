package land.leets.domain.application.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import land.leets.domain.application.type.ApplicationStatus
import land.leets.domain.application.type.Round
import java.time.LocalDateTime

class RecruitScheduleTest : DescribeSpec({

    val schedule = RecruitSchedule()

    val 정규접수 = LocalDateTime.of(2026, 9, 3, 22, 31)
    val 추가접수 = LocalDateTime.of(2026, 9, 5, 10, 0)

    describe("회차 판정은") {
        it("추가 모집 접수 시작(09.04 20:00) 전 접수분을 REGULAR 로 본다") {
            schedule.roundOf(정규접수) shouldBe Round.REGULAR
            schedule.roundOf(LocalDateTime.of(2026, 9, 4, 19, 59)) shouldBe Round.REGULAR
        }
        it("09.04 20:00 이후 접수분을 ADDITIONAL 로 본다") {
            schedule.roundOf(LocalDateTime.of(2026, 9, 4, 20, 0)) shouldBe Round.ADDITIONAL
            schedule.roundOf(추가접수) shouldBe Round.ADDITIONAL
        }
        it("미제출(appliedAt = null)은 REGULAR 로 본다") {
            schedule.roundOf(null) shouldBe Round.REGULAR
        }
    }

    describe("정규 지원자의 서류 결과는") {
        val 발표직전 = LocalDateTime.of(2026, 9, 4, 17, 59, 59)
        val 발표직후 = LocalDateTime.of(2026, 9, 4, 18, 0)

        it("09.04 18:00 전에는 합격도 PENDING 으로 감춘다") {
            schedule.visibleStatus(ApplicationStatus.PASS_PAPER, 정규접수, 발표직전) shouldBe ApplicationStatus.PENDING
        }
        it("09.04 18:00 전에는 불합격도 PENDING 으로 감춘다") {
            schedule.visibleStatus(ApplicationStatus.FAIL_PAPER, 정규접수, 발표직전) shouldBe ApplicationStatus.PENDING
        }
        it("09.04 18:00 부터 공개한다") {
            schedule.visibleStatus(ApplicationStatus.PASS_PAPER, 정규접수, 발표직후) shouldBe ApplicationStatus.PASS_PAPER
            schedule.visibleStatus(ApplicationStatus.FAIL_PAPER, 정규접수, 발표직후) shouldBe ApplicationStatus.FAIL_PAPER
        }
    }

    describe("추가 지원자의 서류 결과는") {
        it("정규 발표(09.04 18:00) 가 지나도 09.07 18:00 전이면 감춘다") {
            val 정규발표후 = LocalDateTime.of(2026, 9, 4, 18, 30)
            schedule.visibleStatus(ApplicationStatus.PASS_PAPER, 추가접수, 정규발표후) shouldBe ApplicationStatus.PENDING
        }
        it("09.07 18:00 부터 공개한다") {
            val 추가발표후 = LocalDateTime.of(2026, 9, 7, 18, 0)
            schedule.visibleStatus(ApplicationStatus.PASS_PAPER, 추가접수, 추가발표후) shouldBe ApplicationStatus.PASS_PAPER
        }
    }

    describe("최종 결과는") {
        val 최종발표전 = LocalDateTime.of(2026, 9, 12, 17, 59)
        val 최종발표후 = LocalDateTime.of(2026, 9, 12, 18, 0)

        it("09.12 18:00 전에는 PASS 를 PASS_PAPER 로 되돌려 면접 정보를 유지한다") {
            schedule.visibleStatus(ApplicationStatus.PASS, 정규접수, 최종발표전) shouldBe ApplicationStatus.PASS_PAPER
        }
        it("09.12 18:00 전에는 FAIL 도 PASS_PAPER 로 감춘다") {
            schedule.visibleStatus(ApplicationStatus.FAIL, 정규접수, 최종발표전) shouldBe ApplicationStatus.PASS_PAPER
        }
        it("09.12 18:00 부터 공개한다") {
            schedule.visibleStatus(ApplicationStatus.PASS, 정규접수, 최종발표후) shouldBe ApplicationStatus.PASS
            schedule.visibleStatus(ApplicationStatus.FAIL, 정규접수, 최종발표후) shouldBe ApplicationStatus.FAIL
        }
        it("추가 지원자도 통합 발표라 09.12 기준을 따른다") {
            schedule.visibleStatus(ApplicationStatus.PASS, 추가접수, 최종발표전) shouldBe ApplicationStatus.PASS_PAPER
            schedule.visibleStatus(ApplicationStatus.PASS, 추가접수, 최종발표후) shouldBe ApplicationStatus.PASS
        }
    }
})
