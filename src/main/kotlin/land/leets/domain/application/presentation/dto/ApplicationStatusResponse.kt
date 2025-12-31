package land.leets.domain.application.presentation.dto

import land.leets.domain.application.domain.Application
import land.leets.domain.application.type.ApplicationStatus
import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.type.HasInterview
import java.time.LocalDateTime

data class ApplicationStatusResponse(
    val id: Long,
    val status: ApplicationStatus,
    val hasInterview: HasInterview?,
    val interviewDate: LocalDateTime?,
    val interviewPlace: String?,
) {
    companion object {
        fun of(
            application: Application,
            interview: Interview?,
        ): ApplicationStatusResponse {
            if (application.applicationStatus != ApplicationStatus.PASS_PAPER) {
                return ApplicationStatusResponse(
                    id = application.id!!,
                    status = application.applicationStatus,
                    hasInterview = null,
                    interviewDate = null,
                    interviewPlace = null,
                )
            }
            return ApplicationStatusResponse(
                id = application.id!!,
                status = application.applicationStatus,
                hasInterview = interview!!.hasInterview,
                interviewDate = interview.fixedInterviewDate,
                interviewPlace = interview.place,
            )
        }
    }
}
