package land.leets.domain.interview.presentation.dto.req

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class InterviewRequest(
    @field:NotNull
    val applicationId: Long,

    @field:NotNull
    val fixedInterviewDate: LocalDateTime,

    @field:NotNull
    val place: String
)
