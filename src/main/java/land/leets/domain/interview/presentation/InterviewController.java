package land.leets.domain.interview.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import land.leets.domain.interview.domain.Interview;
import land.leets.domain.interview.presentation.dto.req.FixedInterviewRequest;
import land.leets.domain.interview.presentation.dto.req.InterviewAttendanceRequest;
import land.leets.domain.interview.type.HasInterview;
import land.leets.domain.interview.usecase.UpdateInterview;
import land.leets.domain.mail.usecase.CreateInterviewRequest;
import land.leets.global.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interview")
@Tag(name = "INTERVIEW")
public class InterviewController {
    private final UpdateInterview updateInterview;
    private final CreateInterviewRequest createInterviewRequest;

    @Operation(summary = "(유저) 면접 참여 여부 변경", description = "면접 참여 여부를 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping
    public Interview update(@RequestBody InterviewAttendanceRequest request) {
        return updateInterview.byUser(request);
    }

    @Operation(summary = "(관리자) 면접 정보 변경", description = "면접 정보를 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}")
    public Interview update(@PathVariable Long id, @RequestBody FixedInterviewRequest request) {
        return updateInterview.byAdmin(id, request);
    }

    @Operation(summary = "(유저) 면접 여부 변경 요청 생성하기", description = "면접 참가 여부 변경 요청을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public RedirectView hasInterview(@RequestParam UUID uid, @RequestParam HasInterview hasInterview) {
        createInterviewRequest.execute(uid, hasInterview);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("https://www.leets.land");
        return redirectView;
    }
}
