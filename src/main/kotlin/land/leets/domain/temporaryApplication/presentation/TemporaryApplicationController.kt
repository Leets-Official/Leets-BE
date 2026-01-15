package land.leets.domain.temporaryApplication.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import land.leets.domain.auth.AuthDetails
import land.leets.domain.temporaryApplication.presentation.dto.TemporaryApplicationRequest
import land.leets.domain.temporaryApplication.presentation.dto.TemporaryApplicationResponse
import land.leets.domain.temporaryApplication.usecase.GetTemporaryApplication
import land.leets.domain.temporaryApplication.usecase.SaveTemporaryApplication
import land.leets.global.error.ErrorResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/temporary-application")
class TemporaryApplicationController(
    private val getTemporaryApplication: GetTemporaryApplication,
    private val saveTemporaryApplication: SaveTemporaryApplication
) {

    @Operation(summary = "(유저) 임시저장 지원서 조회", description = "임시 저장한 지원서를 호출합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "403", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    )
    @GetMapping
    fun get(
        @AuthenticationPrincipal authDetails: AuthDetails
    ): TemporaryApplicationResponse? =
        getTemporaryApplication.execute(authDetails)

    @Operation(summary = "(유저) 지원서 임시 저장", description = "지원서를 작성합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "403", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    )
    @PutMapping
    fun save(
        @AuthenticationPrincipal authDetails: AuthDetails,
        @RequestBody request: TemporaryApplicationRequest
    ): TemporaryApplicationResponse =
        saveTemporaryApplication.execute(authDetails, request)
}
