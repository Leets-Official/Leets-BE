package land.leets.domain.comment.usecase;

import land.leets.domain.comment.domain.Comment;
import land.leets.domain.comment.domain.repository.CommentRepository;
import land.leets.domain.comment.presentation.dto.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCommentsImpl implements GetComments {

    private final CommentRepository commentRepository;

    @Override
    public List<CommentResponse> execute(Long applicationId) {
        List<Comment> comments = commentRepository.findAllByApplicationId(applicationId);
        return comments.stream()
                .map(CommentResponse::from)
                .toList();
    }
}
