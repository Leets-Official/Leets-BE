package land.leets.domain.interview.presentation.dto.req

import jakarta.validation.constraints.NotNull
import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.type.HasInterview
import java.util.UUID

data class InterviewAttendanceRequest(
    @field:NotNull
    val uid: UUID,

    @field:NotNull
    val hasInterview: HasInterview
) {
    fun updateInterview(interview: Interview) {
        interview.hasInterview = this.hasInterview
    }
}
