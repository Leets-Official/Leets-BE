package land.leets.domain.interview.presentation.dto.req

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class InterviewRequest(
    @NotNull
    val applicationId: Long,

    @NotNull
    val fixedInterviewDate: LocalDateTime,

    @NotNull
    val place: String
)
