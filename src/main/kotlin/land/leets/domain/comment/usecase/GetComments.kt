package land.leets.domain.comment.usecase

import land.leets.domain.comment.presentation.dto.CommentResponse

interface GetComments {
    fun execute(applicationId: Long): List<CommentResponse>
}
