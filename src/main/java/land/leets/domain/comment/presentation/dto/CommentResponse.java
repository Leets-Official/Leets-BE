package land.leets.domain.comment.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import land.leets.domain.admin.presentation.dto.AdminDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {

    @NotBlank
    private String content;

    @NotBlank
    private LocalDateTime createdAt;

    @NotBlank
    private LocalDateTime updatedAt;

    @NotNull
    private AdminDetailsResponse admin;
}
