package land.leets.domain.interview.presentation.dto.req

import jakarta.validation.constraints.NotNull
import land.leets.domain.interview.domain.Interview
import java.time.LocalDateTime

data class FixedInterviewRequest(
    @NotNull
    val fixedInterviewDate: LocalDateTime,

    @NotNull
    val place: String
) {
    fun updateInterview(interview: Interview) {
        interview.fixedInterviewDate = this.fixedInterviewDate
        interview.place = this.place
    }
}
