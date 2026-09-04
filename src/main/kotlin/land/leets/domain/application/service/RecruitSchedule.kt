package land.leets.domain.application.service

import land.leets.domain.application.type.ApplicationStatus
import land.leets.domain.application.type.Round
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * 모집 회차별 일정과 결과 공개 시점을 관리한다.
 *
 * 결과는 발표 시각 이전에 응답 본문으로 나가서는 안 된다.
 * 어드민이 발표 전에 심사 결과를 미리 입력하는 운영 방식이라,
 * 화면에서 가리는 것만으로는 개발자 도구로 확인이 가능하다.
 * 클라이언트 시계는 조작할 수 있으므로 판정은 서버가 한다.
 */
@Component
class RecruitSchedule {

    companion object {
        /** 추가 모집 접수 시작 — 이 시각 이후 접수분이 ADDITIONAL */
        private val ADDITIONAL_OPEN_AT: LocalDateTime = LocalDateTime.of(2026, 9, 4, 20, 0)

        /** 서류 발표 시각 */
        private val PAPER_ANNOUNCE_AT = mapOf(
            Round.REGULAR to LocalDateTime.of(2026, 9, 4, 18, 0),
            Round.ADDITIONAL to LocalDateTime.of(2026, 9, 7, 18, 0)
        )

        /** 최종 발표 시각 — 정규·추가 통합 */
        private val FINAL_ANNOUNCE_AT: LocalDateTime = LocalDateTime.of(2026, 9, 12, 18, 0)
    }

    /** 접수 시각으로 회차를 판정한다. 미제출(appliedAt = null)은 정규로 본다. */
    fun roundOf(appliedAt: LocalDateTime?): Round =
        if (appliedAt != null && !appliedAt.isBefore(ADDITIONAL_OPEN_AT)) Round.ADDITIONAL else Round.REGULAR

    fun paperAnnounceAt(round: Round): LocalDateTime = PAPER_ANNOUNCE_AT.getValue(round)

    fun finalAnnounceAt(): LocalDateTime = FINAL_ANNOUNCE_AT

    /**
     * 발표 시각 전에는 확정된 심사 결과를 감춘다.
     *
     * - 서류 발표 전: 무조건 PENDING
     * - 서류 발표 후 ~ 최종 발표 전: 최종 결과(PASS/FAIL)는 PASS_PAPER 로 되돌린다.
     *   최종 결과가 나온 지원자는 서류를 통과한 사람이므로, 면접 정보는 계속 보여야 한다.
     * - 최종 발표 후: 그대로 공개
     */
    fun visibleStatus(
        status: ApplicationStatus,
        appliedAt: LocalDateTime?,
        now: LocalDateTime = LocalDateTime.now()
    ): ApplicationStatus {
        if (now.isBefore(paperAnnounceAt(roundOf(appliedAt)))) return ApplicationStatus.PENDING

        val isFinal = status == ApplicationStatus.PASS || status == ApplicationStatus.FAIL
        if (isFinal && now.isBefore(FINAL_ANNOUNCE_AT)) return ApplicationStatus.PASS_PAPER

        return status
    }
}
