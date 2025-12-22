package land.leets.domain.user.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import land.leets.domain.auth.AuthDetails
import land.leets.domain.auth.AuthService
import land.leets.domain.shared.AuthRole
import land.leets.domain.user.presentation.dto.LoginRequest
import land.leets.domain.user.presentation.dto.UserDetailsResponse
import land.leets.domain.user.usecase.GetUserDetails
import land.leets.global.error.ErrorResponse
import land.leets.global.jwt.JwtProvider
import land.leets.global.jwt.dto.JwtResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.io.IOException
import java.security.GeneralSecurityException

@RestController
@RequestMapping("/user")
class UserController(
    private val getUserDetails: GetUserDetails,
    private val authService: AuthService,
    private val jwtProvider: JwtProvider
) {

    @Operation(summary = "(비로그인) 유저 로그인", description = "유저 계정으로 로그인합니다.")
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
    @Throws(GeneralSecurityException::class, IOException::class)
    fun login(@RequestBody request: LoginRequest): JwtResponse {
        val user = authService.getUser(request.idToken)

        val accessToken = jwtProvider.generateToken(user.uid, user.email, AuthRole.ROLE_USER, false)
        val refreshToken = jwtProvider.generateToken(user.uid, user.email, AuthRole.ROLE_USER, true)

        return JwtResponse(accessToken, refreshToken)
    }

    @Operation(summary = "(유저) 유저 정보 상세 조회", description = "유저 정보를 상세 조회합니다.")
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
    fun getUserDetails(@AuthenticationPrincipal authDetails: AuthDetails): UserDetailsResponse {
        return getUserDetails.execute(authDetails)
    }
}
