package land.leets.domain.auth.presentation;

import land.leets.domain.auth.AuthService;
import land.leets.domain.auth.presentation.dto.OAuthTokenDto;
import land.leets.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/oauth2/callback/google")
    public User successGoogleLogin(@RequestParam("code") String code) {

        return authService.getGoogleAccessToken(code);
    }
}
