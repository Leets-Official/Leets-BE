package land.leets.domain.comment.domain.repository

import land.leets.domain.comment.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByApplicationId(applicationId: Long): List<Comment>
}
