package land.leets.domain.comment.domain.repository;

import land.leets.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByApplicationId(Long applicationId);
}
