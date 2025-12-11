package land.leets.domain.comment.presentation.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CommentRequest(
    @field:NotNull
    val applicationId: Long,

    @field:NotBlank
    val content: String
)