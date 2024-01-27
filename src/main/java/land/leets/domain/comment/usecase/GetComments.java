package land.leets.domain.comment.usecase;

import land.leets.domain.comment.presentation.dto.CommentResponse;

import java.util.List;

public interface GetComments {
    List<CommentResponse> execute(Long applicationId);
}
