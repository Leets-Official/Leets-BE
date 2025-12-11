package land.leets.domain.comment.usecase

import land.leets.domain.auth.AuthDetails
import land.leets.domain.comment.presentation.dto.CommentRequest
import land.leets.domain.comment.presentation.dto.CommentResponse

interface CreateComment {
    fun execute(authDetails: AuthDetails, request: CommentRequest): CommentResponse
}
