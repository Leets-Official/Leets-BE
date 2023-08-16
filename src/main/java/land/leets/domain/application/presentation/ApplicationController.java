package land.leets.domain.application.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import land.leets.domain.application.domain.Application;
import land.leets.domain.application.presentation.dto.ApplicationRequest;
import land.leets.domain.application.presentation.dto.ApplicationResponse;
import land.leets.domain.application.presentation.dto.StatusRequest;
import land.leets.domain.application.usecase.*;
import land.leets.domain.auth.AuthDetails;
import land.leets.global.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
public class ApplicationController {

    private final CreateApplication createApplication;
    private final UpdateApplication updateApplication;
    private final GetAllApplication getApplication;
    private final GetApplicationDetails getApplicationDetails;
    private final UpdateResult updateResult;

    @Operation(summary = "(유저) 지원서 작성", description = "지원서를 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping()
    public Application create(@AuthenticationPrincipal AuthDetails authDetails, @RequestBody ApplicationRequest request) {
        return createApplication.execute(authDetails, request);
    }

    @Operation(summary = "(유저) 지원서 수정", description = "지원서를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping()
    public Application update(@AuthenticationPrincipal AuthDetails authDetails, @RequestBody ApplicationRequest request) {
        return updateApplication.execute(authDetails, request);
    }

    @Operation(summary = "(관리자) 지원서 목록 조회", description = "all, develop, design 포지션 별 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping()
    public List<ApplicationResponse> get(@RequestParam(required = false) String position,
                                         @RequestParam(required = false) String status) {
        if (position == null && status == null) return getApplication.execute();
        return getApplication.execute(position, status);
    }

    @Operation(summary = "(관리자) 지원서 상세 조회", description = "지원서를 상세 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public Application get(@PathVariable Long id) {
        return getApplicationDetails.execute(id);
    }

    @Operation(summary = "(관리자) 지원 상태 변경", description = "지원 상태를 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}")
    public Application get(@PathVariable Long id, @RequestBody StatusRequest request) {
        return updateResult.execute(id, request);
    }

    @Operation(summary = "(유저) 지원서 불러오기", description = "작성한 지원서를 불러옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/me")
    public Application get(@AuthenticationPrincipal AuthDetails authDetails) {
        UUID uid = authDetails.getUid();
        return getApplicationDetails.execute(uid);
    }
}
