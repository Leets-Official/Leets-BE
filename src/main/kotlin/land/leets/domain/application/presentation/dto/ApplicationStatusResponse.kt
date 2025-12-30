package land.leets.domain.application.presentation.dto

import land.leets.domain.application.domain.Application
import land.leets.domain.application.type.ApplicationStatus

data class ApplicationStatusResponse(
    val id: Long,
    val status: ApplicationStatus,
    val interviewDay: String?,
    val interviewTime: String?,
) {
    companion object {
        fun from(
            application: Application,
        ): ApplicationStatusResponse {
            if (application.applicationStatus != ApplicationStatus.PASS_PAPER) {
                return ApplicationStatusResponse(
                    id = application.id!!,
                    status = application.applicationStatus,
                    interviewDay = null,
                    interviewTime = null,
                )
            }
            return ApplicationStatusResponse(
                id = application.id!!,
                status = application.applicationStatus,
                interviewDay = application.interviewDay,
                interviewTime = application.interviewTime,
            )
        }
    }
}
