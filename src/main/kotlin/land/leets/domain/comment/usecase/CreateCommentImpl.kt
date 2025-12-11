package land.leets.domain.comment.usecase

import land.leets.domain.admin.domain.repository.AdminRepository
import land.leets.domain.admin.exception.AdminNotFoundException
import land.leets.domain.auth.AuthDetails
import land.leets.domain.comment.domain.Comment
import land.leets.domain.comment.domain.repository.CommentRepository
import land.leets.domain.comment.presentation.dto.CommentRequest
import land.leets.domain.comment.presentation.dto.CommentResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateCommentImpl(
    private val adminRepository: AdminRepository,
    private val commentRepository: CommentRepository
) : CreateComment {
    @Transactional
    override fun execute(authDetails: AuthDetails, request: CommentRequest): CommentResponse {
        val uid = authDetails.uid
        val admin = adminRepository.findById(uid).orElseThrow { AdminNotFoundException() }

        val comment = Comment(
            applicationId = request.applicationId,
            content = request.content,
            admin = admin
        )
        val savedComment = commentRepository.save(comment)
        return CommentResponse.from(savedComment)
    }
}
