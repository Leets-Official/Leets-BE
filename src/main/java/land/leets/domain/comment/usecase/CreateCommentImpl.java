package land.leets.domain.comment.usecase;

import land.leets.domain.admin.domain.Admin;
import land.leets.domain.admin.domain.repository.AdminRepository;
import land.leets.domain.admin.exception.AdminNotFoundException;
import land.leets.domain.auth.AuthDetails;
import land.leets.domain.comment.domain.Comment;
import land.leets.domain.comment.domain.repository.CommentRepository;
import land.leets.domain.comment.presentation.dto.CommentRequest;
import land.leets.domain.comment.presentation.dto.CommentResponse;
import land.leets.domain.comment.presentation.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCommentImpl implements CreateComment {

    private final CommentRepository commentRepository;
    private final AdminRepository adminRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponse execute(AuthDetails authDetails, CommentRequest request) {
        Admin admin = adminRepository.findById(authDetails.getUid()).orElseThrow(AdminNotFoundException::new);
        Comment comment = Comment.builder()
                .applicationId(request.getApplicationId())
                .content(request.getContent())
                .admin(admin)
                .build();
        Comment save = commentRepository.save(comment);
        return commentMapper.mappingCommentToDto(save);
    }
}
