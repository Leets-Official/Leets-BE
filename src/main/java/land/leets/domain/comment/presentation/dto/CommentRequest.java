package land.leets.domain.comment.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequest {
    @NotBlank
    private Long applicationId;

    @NotBlank
    private String content;
}
