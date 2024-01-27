package land.leets.domain.comment.usecase;

import land.leets.domain.auth.AuthDetails;
import land.leets.domain.comment.presentation.dto.CommentRequest;
import land.leets.domain.comment.presentation.dto.CommentResponse;

public interface CreateComment {
    CommentResponse execute(AuthDetails authDetails, CommentRequest request);
}
