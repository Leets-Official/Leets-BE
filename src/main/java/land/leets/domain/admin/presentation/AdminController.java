package land.leets.domain.admin.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import land.leets.domain.admin.presentation.dto.AdminLoginRequest;
import land.leets.domain.admin.usercase.AdminLogin;
import land.leets.domain.admin.usercase.AdminRefreshToken;
import land.leets.global.error.ErrorResponse;
import land.leets.global.jwt.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminLogin adminLogin;
    private final AdminRefreshToken adminRefreshToken;

    @Operation(summary = "(비로그인) 관리자 로그인", description = "관리자 계정으로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public JwtResponse login(HttpServletResponse res, @Validated @RequestBody AdminLoginRequest request) {
        JwtResponse jwt = adminLogin.execute(request.getId(), request.getPassword());
        Cookie cookie = new Cookie("refreshToken", jwt.getRefreshToken());
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 30);
        res.addCookie(cookie);
        return jwt;
    }

    @Operation(summary = "(관리자) 관리자 토큰 갱신", description = "관리자 계정의 AccessToken을 갱신합니다. Cookie에 저장된 RefreshToken을 자동으로 사용합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/refresh")
    public JwtResponse refresh(@Parameter(hidden = true) @CookieValue("refreshToken") String tokenInput) {
        return adminRefreshToken.execute(tokenInput);
    }
}
