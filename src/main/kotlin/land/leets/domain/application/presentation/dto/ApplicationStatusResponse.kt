package land.leets.domain.application.presentation.dto

import land.leets.domain.application.domain.Application
import land.leets.domain.application.type.ApplicationStatus
import land.leets.domain.application.type.Round
import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.type.HasInterview
import java.time.LocalDateTime

data class ApplicationStatusResponse(
    val id: Long,
    val status: ApplicationStatus,
    val round: Round,
    val hasInterview: HasInterview?,
    val interviewDate: LocalDateTime?,
    val interviewPlace: String?,
) {
    companion object {
        fun of(
            application: Application,
            interview: Interview?,
            visibleStatus: ApplicationStatus,
            round: Round,
        ): ApplicationStatusResponse {
            // 서류 합격이 공개된 뒤에만 면접 정보를 함께 내려준다.
            if (visibleStatus != ApplicationStatus.PASS_PAPER || interview == null) {
                return ApplicationStatusResponse(
                    id = application.id!!,
                    status = visibleStatus,
                    round = round,
                    hasInterview = null,
                    interviewDate = null,
                    interviewPlace = null,
                )
            }
            return ApplicationStatusResponse(
                id = application.id!!,
                status = visibleStatus,
                round = round,
                hasInterview = interview.hasInterview,
                interviewDate = interview.fixedInterviewDate,
                interviewPlace = interview.place,
            )
        }
    }
}
