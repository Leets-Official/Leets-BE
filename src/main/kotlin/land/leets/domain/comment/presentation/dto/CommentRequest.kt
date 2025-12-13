package land.leets.domain.comment.presentation.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CommentRequest(
    @NotNull
    val applicationId: Long,

    @NotBlank
    val content: String
)