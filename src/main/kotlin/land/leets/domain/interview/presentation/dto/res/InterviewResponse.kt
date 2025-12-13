package land.leets.domain.interview.presentation.dto.res

import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.type.HasInterview
import java.time.LocalDateTime

data class InterviewResponse(
    val hasInterview: HasInterview?,
    val fixedInterviewDate: LocalDateTime?
) {
    companion object {
        fun from(interview: Interview): InterviewResponse {
            return InterviewResponse(
                hasInterview = interview.hasInterview,
                fixedInterviewDate = interview.fixedInterviewDate
            )
        }
    }
}