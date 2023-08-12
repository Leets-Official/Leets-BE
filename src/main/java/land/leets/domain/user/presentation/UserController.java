package land.leets.domain.user.presentation;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import land.leets.domain.auth.AuthDetails;
import land.leets.domain.auth.AuthService;
import land.leets.domain.shared.AuthRole;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.presentation.dto.LoginRequest;
import land.leets.domain.user.presentation.dto.UserDetailsResponse;
import land.leets.domain.user.usecase.GetUserDetails;
import land.leets.global.error.ErrorResponse;
import land.leets.global.jwt.JwtProvider;
import land.leets.global.jwt.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final GetUserDetails getUserDetails;
    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @Operation(summary = "(비로그인) 유저 로그인", description = "유저 계정으로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public JwtResponse getUserDetails(@RequestBody LoginRequest request) throws GeneralSecurityException, IOException {
        User user = authService.getUser(request.getIdToken());

        String accessToken = this.jwtProvider.generateToken(user.getUid(), user.getSub(), AuthRole.ROLE_USER, false);
        String refreshToken = this.jwtProvider.generateToken(user.getUid(), user.getSub(), AuthRole.ROLE_USER, true);

        return new JwtResponse(accessToken, refreshToken);
    }

    @Operation(summary = "(유저) 유저 정보 상세 조회", description = "유저 정보를 상세 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/me")
    public UserDetailsResponse getUserDetails(@AuthenticationPrincipal AuthDetails authDetails) {
        return getUserDetails.execute(authDetails);
    }
}