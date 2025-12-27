package land.leets.domain.comment.presentation.dto

import land.leets.domain.admin.presentation.dto.AdminDetailsResponse
import land.leets.domain.comment.domain.Comment
import java.time.LocalDateTime

data class CommentResponse(
    val content: String,

    val createdAt: LocalDateTime?,

    val updatedAt: LocalDateTime?,

    val admin: AdminDetailsResponse
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                content = comment.content,
                createdAt = comment.createdAt,
                updatedAt = comment.updatedAt,
                admin = AdminDetailsResponse.from(comment.admin)
            )
        }
    }
}
