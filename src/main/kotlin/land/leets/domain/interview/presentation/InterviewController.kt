package land.leets.domain.interview.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.presentation.dto.req.FixedInterviewRequest
import land.leets.domain.interview.presentation.dto.req.InterviewAttendanceRequest
import land.leets.domain.interview.presentation.dto.req.InterviewRequest
import land.leets.domain.interview.usecase.CreateInterview
import land.leets.domain.interview.usecase.UpdateInterview
import land.leets.global.error.ErrorResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/interview")
class InterviewController(
    private val updateInterview: UpdateInterview,
    private val createInterview: CreateInterview
) {

    @Operation(summary = "(유저) 면접 참여 여부 변경", description = "면접 참여 여부를 변경합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200"),
            ApiResponse(
                responseCode = "400",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @PatchMapping
    fun update(@Valid @RequestBody request: InterviewAttendanceRequest): Interview {
        return updateInterview.byUser(request)
    }

    @Operation(summary = "(관리자) 면접 정보 변경", description = "면접 정보를 변경합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200"),
            ApiResponse(
                responseCode = "400",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @PatchMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody request: FixedInterviewRequest): Interview {
        return updateInterview.byAdmin(id, request)
    }

    @Operation(summary = "(관리자) 면접 정보 생성", description = "면접 정보를 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200"),
            ApiResponse(
                responseCode = "400",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @PostMapping
    fun create(@Valid @RequestBody request: InterviewRequest): Interview {
        return createInterview.execute(request)
    }
}
