package land.leets.domain.auth.presentation;

import land.leets.domain.auth.AuthService;
import land.leets.domain.shared.AuthRole;
import land.leets.domain.user.domain.User;
import land.leets.global.jwt.JwtProvider;
import land.leets.global.jwt.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;


    @GetMapping("/login/oauth2/callback/google")
    public JwtResponse successGoogleLogin(@RequestParam("code") String code) {
        User user = authService.getGoogleAccessToken(code);

        String accessToken = this.jwtProvider.generateToken(user.getUid(), user.getSub(), AuthRole.ROLE_USER, false);
        String refreshToken = this.jwtProvider.generateToken(user.getUid(), user.getSub(), AuthRole.ROLE_USER, true);

        return new JwtResponse(accessToken, refreshToken);
    }
}
