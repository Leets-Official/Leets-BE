package land.leets.domain.application.presentation.dto

import land.leets.domain.application.domain.Application
import land.leets.domain.application.type.ApplicationStatus
import land.leets.domain.application.type.Position
import land.leets.domain.interview.presentation.dto.res.InterviewResponse

data class ApplicationResponse(
    val id: Long,
    val name: String,
    val grade: String,
    val position: Position,
    val career: String?,
    val applicationStatus: ApplicationStatus,
    val phone: String,
    val interview: InterviewResponse?
) {
    companion object {
        fun of(application: Application, interview: InterviewResponse?, phone: String): ApplicationResponse {
            return ApplicationResponse(
                id = application.id!!,
                name = application.name,
                grade = application.grade,
                position = application.position,
                career = application.career,
                applicationStatus = application.applicationStatus,
                phone = phone,
                interview = interview
            )
        }
    }
}
