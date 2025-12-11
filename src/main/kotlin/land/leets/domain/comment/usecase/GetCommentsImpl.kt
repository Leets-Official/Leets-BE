package land.leets.domain.comment.usecase

import land.leets.domain.comment.domain.repository.CommentRepository
import land.leets.domain.comment.presentation.dto.CommentResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetCommentsImpl(
    private val commentRepository: CommentRepository
) : GetComments {
    @Transactional(readOnly = true)
    override fun execute(applicationId: Long): List<CommentResponse> {
        val comments = commentRepository.findAllByApplicationId(applicationId)
        return comments.map { CommentResponse.from(it) }
    }
}
