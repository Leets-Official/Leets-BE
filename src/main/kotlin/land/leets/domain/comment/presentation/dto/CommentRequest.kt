package land.leets.domain.comment.presentation.dto

import jakarta.validation.constraints.NotBlank

data class CommentRequest(
    @field:NotBlank
    val applicationId: Long,

    @field:NotBlank
    val content: String
)