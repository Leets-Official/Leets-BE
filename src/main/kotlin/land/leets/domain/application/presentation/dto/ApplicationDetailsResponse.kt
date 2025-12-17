package land.leets.domain.application.presentation.dto

import land.leets.domain.application.domain.Application
import land.leets.domain.application.type.ApplicationStatus
import land.leets.domain.application.type.Position
import land.leets.domain.application.type.SubmitStatus
import land.leets.domain.interview.presentation.dto.res.InterviewDetailsResponse
import java.time.LocalDateTime

data class ApplicationDetailsResponse(
    val id: Long,
    val phone: String,
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
    val applicationStatus: ApplicationStatus,
    val submitStatus: SubmitStatus,
    val appliedAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val interview: InterviewDetailsResponse?
) {
    companion object {
        fun of(
            application: Application,
            interview: InterviewDetailsResponse?,
            phone: String
        ): ApplicationDetailsResponse {
            return ApplicationDetailsResponse(
                id = application.id!!,
                phone = phone,
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
                applicationStatus = application.applicationStatus,
                submitStatus = application.submitStatus,
                appliedAt = application.appliedAt,
                updatedAt = application.updatedAt,
                interview = interview
            )
        }
    }
}
