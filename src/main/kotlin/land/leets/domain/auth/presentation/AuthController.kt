package land.leets.domain.auth.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import land.leets.domain.auth.AuthService
import land.leets.domain.shared.AuthRole
import land.leets.global.error.ErrorResponse
import land.leets.global.jwt.JwtProvider
import land.leets.global.jwt.dto.JwtResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
    private val jwtProvider: JwtProvider
) {

    @Operation(summary = "(로그인) 유저 로그인", description = "구글 토큰으로 로그인합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200"),
            ApiResponse(
                responseCode = "400",
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
    @GetMapping("/login/oauth2/callback/google")
    fun get(@RequestParam("code") code: String): JwtResponse {
        val user = authService.getGoogleToken(code)
        val accessToken = jwtProvider.generateToken(user.id!!, user.email, AuthRole.ROLE_USER, false)
        val refreshToken = jwtProvider.generateToken(user.id, user.email, AuthRole.ROLE_USER, true)

        return JwtResponse(accessToken, refreshToken)
    }

    @GetMapping("/health-check")
    fun checkHealthStatus(): ResponseEntity<Void> {
        return ResponseEntity(HttpStatus.OK)
    }
}
