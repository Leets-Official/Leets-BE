package land.leets.domain.storage.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import land.leets.domain.storage.presentation.dto.PreAuthenticatedUrlResponse
import land.leets.domain.storage.usecase.GetPreAuthenticatedUrl
import land.leets.global.error.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/storages")
class StorageController(
    private val getPreAuthenticatedUrl: GetPreAuthenticatedUrl
) {

    @Operation(summary = "Pre-Authenticated Url 발급", description = "OCI Object Storage에 파일 업로드를 위한 Pre-Authenticated Url을 발급합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "403", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    )
    @PostMapping("/pre-authenticated-url")
    fun getPresignedUrl(
        @Parameter(description = "버킷에 업로드할 파일 이름 및 경로", example = "profile/{username}.jpg")
        @RequestParam fileName: String
    ): ResponseEntity<PreAuthenticatedUrlResponse> {
        return ResponseEntity.ok(getPreAuthenticatedUrl.execute(fileName))
    }
}
