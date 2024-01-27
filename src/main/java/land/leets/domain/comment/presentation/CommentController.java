package land.leets.domain.comment.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import land.leets.domain.auth.AuthDetails;
import land.leets.domain.comment.presentation.dto.CommentRequest;
import land.leets.domain.comment.presentation.dto.CommentResponse;
import land.leets.domain.comment.usecase.CreateComment;
import land.leets.domain.comment.usecase.GetComments;
import land.leets.global.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CreateComment createComment;
    private final GetComments getComments;

    @Operation(summary = "(관리자) 댓글 작성", description = "지원서에 댓글을 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public CommentResponse create(@AuthenticationPrincipal AuthDetails authDetails,
                                  @RequestBody CommentRequest request) {
        return createComment.execute(authDetails, request);
    }

    @Operation(summary = "(관리자) 댓글 조회", description = "지원서 댓글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{applicationId}")
    public List<CommentResponse> get(@PathVariable Long applicationId) {
        return getComments.execute(applicationId);
    }
}
