package land.leets.domain.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import land.leets.domain.auth.exception.CookieNotFoundException;
import land.leets.domain.user.UserRepository;
import land.leets.global.auth.CustomUserDetails;
import land.leets.global.jwt.JwtTokenProvider;
import land.leets.global.jwt.exception.InvalidTokenException;
import land.leets.global.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${jwt.auth.cookie_key}")
    private String cookieKey;

    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    public String refreshToken(HttpServletRequest request, HttpServletResponse response, String oldAccessToken) {
        // 1. Validation Refresh Token
        String oldRefreshToken = CookieUtil.getCookie(request, cookieKey).map(Cookie::getValue)
                .orElseThrow(CookieNotFoundException::new);

        if (!tokenProvider.validateToken(oldRefreshToken)) {
            throw new InvalidTokenException();
        }

        // 2. 유저정보 얻기
        Authentication authentication = tokenProvider.getAuthentication(oldAccessToken);
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        Long id = Long.valueOf(user.getName());

        // 3. Match Refresh Token
        String savedToken = userRepository.getRefreshTokenById(id);

        if (!savedToken.equals(oldRefreshToken)) {
            throw new InvalidTokenException();
        }

        // 4. JWT 갱신
        String accessToken = tokenProvider.generateToken(authentication);
        tokenProvider.createRefreshToken(authentication, response);

        return accessToken;
    }
}