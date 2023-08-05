package land.leets.domain.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import land.leets.domain.user.domain.repository.UserRepository;
import land.leets.global.auth.CustomUserDetails;
import land.leets.global.auth.exception.CookieNotFoundException;
import land.leets.global.jwt.JwtProvider;
import land.leets.global.jwt.exception.InvalidTokenException;
import land.leets.global.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${jwt.auth.cookie_key}")
    private String cookieKey;
    private final UserRepository userRepository;
    private final JwtProvider tokenProvider;

    public String refreshToken(HttpServletRequest request, HttpServletResponse response, String oldAccessToken) {
        // 1. Validation Refresh Token
        String oldRefreshToken = CookieUtil.getCookie(request, cookieKey).map(Cookie::getValue)
                .orElseThrow(CookieNotFoundException::new);

        if (!tokenProvider.validateToken(oldRefreshToken, true)) {
            throw new InvalidTokenException();
        }

        // 2. 유저정보 얻기
        Authentication authentication = tokenProvider.getAuthentication(oldAccessToken);
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        UUID uid = UUID.fromString(user.getName());


        // 3. Match Refresh Token
        String savedToken = userRepository.getRefreshTokenById(uid);

        if (!savedToken.equals(oldRefreshToken)) {
            throw new InvalidTokenException();
        }

        // 4. JWT 갱신
        String accessToken = tokenProvider.generateAccessToken(authentication);
        tokenProvider.generateRefreshToken(authentication, response);

        return accessToken;
    }
}