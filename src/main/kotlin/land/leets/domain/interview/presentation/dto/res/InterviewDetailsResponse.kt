package land.leets.domain.interview.presentation.dto.res

import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.type.HasInterview
import java.time.LocalDateTime

data class InterviewDetailsResponse(
    val id: Long?,
    val hasInterview: HasInterview?,
    val fixedInterviewDate: LocalDateTime?,
    val place: String?
) {
    companion object {
        fun from(interview: Interview): InterviewDetailsResponse {
            return InterviewDetailsResponse(
                id = interview.id,
                hasInterview = interview.hasInterview,
                fixedInterviewDate = interview.fixedInterviewDate,
                place = interview.place
            )
        }
    }
}