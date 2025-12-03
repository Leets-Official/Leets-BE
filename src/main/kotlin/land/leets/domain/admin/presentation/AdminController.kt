package land.leets.domain.admin.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import land.leets.domain.admin.presentation.dto.AdminDetailsResponse
import land.leets.domain.admin.presentation.dto.AdminLoginRequest
import land.leets.domain.admin.usecase.AdminLogin
import land.leets.domain.admin.usecase.AdminRefreshToken
import land.leets.domain.admin.usecase.GetAdminDetails
import land.leets.domain.auth.AuthDetails
import land.leets.global.error.ErrorResponse
import land.leets.global.jwt.dto.JwtResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class AdminController(
    private val adminLogin: AdminLogin,
    private val adminRefreshToken: AdminRefreshToken,
    private val getAdminDetails: GetAdminDetails
) {

    @Operation(summary = "(비로그인) 관리자 로그인", description = "관리자 계정으로 로그인합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200"),
            ApiResponse(responseCode = "400", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "403", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "500", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
        ]
    )
    @PostMapping("/login")
    fun login(res: HttpServletResponse, @Validated @RequestBody request: AdminLoginRequest): JwtResponse {
        val jwt = adminLogin.execute(request.id, request.password)
        val cookie = Cookie("refreshToken", jwt.refreshToken).apply {
            secure = false
            isHttpOnly = true
            maxAge = 60 * 60 * 24 * 30
        }
        res.addCookie(cookie)
        return jwt
    }

    @Operation(summary = "(관리자) 관리자 토큰 갱신", description = "관리자 계정의 AccessToken을 갱신합니다. Cookie에 저장된 RefreshToken을 자동으로 사용합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200"),
            ApiResponse(responseCode = "401", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "403", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "500", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
        ]
    )
    @PostMapping("/refresh")
    fun refresh(@Parameter(hidden = true) @CookieValue("refreshToken") tokenInput: String): JwtResponse {
        return adminRefreshToken.execute(tokenInput)
    }

    @Operation(summary = "(관리자) 관리자 정보 상세 조회", description = "관리자 정보를 상세 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200"),
            ApiResponse(responseCode = "400", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "403", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "500", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
        ]
    )
    @GetMapping("/me")
    fun getAdminDetails(@AuthenticationPrincipal authDetails: AuthDetails): AdminDetailsResponse {
        return getAdminDetails.execute(authDetails)
    }
}