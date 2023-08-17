package land.leets.domain.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import land.leets.domain.auth.AuthService;
import land.leets.domain.shared.AuthRole;
import land.leets.domain.user.domain.User;
import land.leets.global.error.ErrorResponse;
import land.leets.global.jwt.JwtProvider;
import land.leets.global.jwt.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @Operation(summary = "(로그인) 유저 로그인", description = "구글 토큰으로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/login/oauth2/callback/google")
    public JwtResponse get(@RequestParam("code") String code) throws GeneralSecurityException, IOException {
        User user = authService.getGoogleToken(code);
        String accessToken = this.jwtProvider.generateToken(user.getUid(), user.getEmail(), AuthRole.ROLE_USER, false);
        String refreshToken = this.jwtProvider.generateToken(user.getUid(), user.getEmail(), AuthRole.ROLE_USER, true);

        return new JwtResponse(accessToken, refreshToken);
    }
}
