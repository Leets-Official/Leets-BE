package land.leets.domain.application.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import land.leets.domain.application.domain.Application
import land.leets.domain.application.presentation.dto.ApplicationDetailsResponse
import land.leets.domain.application.presentation.dto.ApplicationRequest
import land.leets.domain.application.presentation.dto.ApplicationResponse
import land.leets.domain.application.presentation.dto.ApplicationStatusResponse
import land.leets.domain.application.presentation.dto.StatusRequest
import land.leets.domain.application.usecase.*
import land.leets.domain.auth.AuthDetails
import land.leets.global.error.ErrorResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/application")
class ApplicationController(
    private val createApplication: CreateApplication,
    private val updateApplication: UpdateApplication,
    private val getApplication: GetAllApplication,
    private val getApplicationDetails: GetApplicationDetails,
    private val updateResult: UpdateResult,
    private val getApplicationStatus: GetApplicationStatus,
) {

    @Operation(summary = "(유저) 지원서 작성", description = "지원서를 작성합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "403", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    )
    @PostMapping
    fun create(
        @AuthenticationPrincipal authDetails: AuthDetails,
        @RequestBody request: ApplicationRequest
    ): Application {
        return createApplication.execute(authDetails, request)
    }

    @Operation(summary = "(유저) 지원서 수정", description = "지원서를 수정합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "403", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    )
    @PatchMapping
    fun update(
        @AuthenticationPrincipal authDetails: AuthDetails,
        @RequestBody request: ApplicationRequest
    ): Application {
        return updateApplication.execute(authDetails, request)
    }

    @Operation(summary = "(관리자) 지원서 목록 조회", description = "all, develop, design 포지션 별 조회합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "403", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    )
    @GetMapping
    fun get(
        @RequestParam(required = false) position: String?,
        @RequestParam(required = false) status: String?
    ): List<ApplicationResponse> {
        if (position == null && status == null) return getApplication.execute()
        return getApplication.execute(position, status)
    }

    @Operation(summary = "(관리자) 지원서 상세 조회", description = "지원서를 상세 조회합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "403", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    )
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ApplicationDetailsResponse {
        return getApplicationDetails.execute(id)
    }

    @Operation(summary = "(관리자) 지원 상태 변경", description = "지원 상태를 변경합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "403", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    )
    @PatchMapping("/{id}")
    fun get(@PathVariable id: Long, @Valid @RequestBody request: StatusRequest): Application {
        return updateResult.execute(id, request)
    }

    @Operation(summary = "(유저) 지원서 불러오기", description = "작성한 지원서를 불러옵니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "403", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    )
    @GetMapping("/me")
    fun get(@AuthenticationPrincipal authDetails: AuthDetails): Application {
        val uid = authDetails.uid
        return getApplicationDetails.execute(uid)
    }

    @Operation(summary = "(유저) 지원서 상태 불러오기", description = "작성한 지원서 상태를 불러옵니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "403", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    )
    @GetMapping("/status")
    fun getStatus(@AuthenticationPrincipal authDetails: AuthDetails): ApplicationStatusResponse {
        val uid = authDetails.uid
        return getApplicationStatus.execute(uid)
    }
}
