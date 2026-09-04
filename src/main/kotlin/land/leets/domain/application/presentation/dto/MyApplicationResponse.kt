package land.leets.domain.application.presentation.dto

import land.leets.domain.application.domain.Application
import land.leets.domain.application.type.ApplicationStatus
import land.leets.domain.application.type.Position
import land.leets.domain.application.type.Round
import land.leets.domain.application.type.SubmitStatus
import land.leets.domain.user.domain.User
import java.time.LocalDateTime

/**
 * 본인 지원서 조회(GET /application/me) 응답.
 *
 * 기존에는 Application 엔티티를 그대로 직렬화해 확정된 applicationStatus 가
 * 발표 전에도 응답 본문에 실려 나갔다. 필드 구성은 그대로 두고
 * applicationStatus 만 공개 가능한 값으로 치환한다.
 */
data class MyApplicationResponse(
    val id: Long,
    val user: User,
    val name: String,
    val major: String,
    val grade: String,
    val project: String?,
    val algorithm: String?,
    val portfolio: String?,
    val position: Position,
    val career: String?,
    val interviewDay: String,
    val interviewTime: String,
    val motive: String,
    val expectation: String,
    val capability: String,
    val conflict: String,
    val passion: String,
    val appliedAt: LocalDateTime?,
    val applicationStatus: ApplicationStatus,
    val submitStatus: SubmitStatus,
    val round: Round,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun of(
            application: Application,
            visibleStatus: ApplicationStatus,
            round: Round
        ): MyApplicationResponse = MyApplicationResponse(
            id = application.id!!,
            user = application.user,
            name = application.name,
            major = application.major,
            grade = application.grade,
            project = application.project,
            algorithm = application.algorithm,
            portfolio = application.portfolio,
            position = application.position,
            career = application.career,
            interviewDay = application.interviewDay,
            interviewTime = application.interviewTime,
            motive = application.motive,
            expectation = application.expectation,
            capability = application.capability,
            conflict = application.conflict,
            passion = application.passion,
            appliedAt = application.appliedAt,
            applicationStatus = visibleStatus,
            submitStatus = application.submitStatus,
            round = round,
            createdAt = application.createdAt,
            updatedAt = application.updatedAt
        )
    }
}
