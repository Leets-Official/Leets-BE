package land.leets.global.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletResponse;
import land.leets.domain.user.UserRepository;
import land.leets.global.auth.CustomUserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
public class JwtTokenProvider {

    private static String SECRET_KEY;
    private static String COOKIE_REFRESH_TOKEN_KEY;
    private final UserRepository userRepository;
    private final Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60;		// 1hour
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 7;	// 1week
    private final String AUTHORITIES_KEY = "role";


    public JwtTokenProvider(@Value("${jwt.auth.access_secret}") String secretKey,
                            @Value("${jwt.auth.cookie_key}") String cookieKey,
                            UserRepository userRepository) {
        SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getBytes());
        COOKIE_REFRESH_TOKEN_KEY = cookieKey;
        this.userRepository = userRepository;
    }

    public String generateToken(Authentication authentication) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH);

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        String userId = user.getName();
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(userId)
                .claim(AUTHORITIES_KEY, role)
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();
    }

    public void createRefreshToken(Authentication authentication, HttpServletResponse response) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_LENGTH);

        String refreshToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setExpiration(validity)
                .compact();

        saveRefreshToken(authentication, refreshToken);

        ResponseCookie cookie = ResponseCookie.from(COOKIE_REFRESH_TOKEN_KEY, refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .maxAge(REFRESH_TOKEN_EXPIRE_LENGTH/1000)
                .path("/")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    private void saveRefreshToken(Authentication authentication, String refreshToken) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Long id = Long.valueOf(user.getName());

        userRepository.updateRefreshToken(id, refreshToken);
    }

    // Access Token을 검사하고 얻은 정보로 Authentication 객체 생성
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(claims.get(AUTHORITIES_KEY).toString()));
        CustomUserDetails principal = new CustomUserDetails(Long.valueOf(claims.getSubject()), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalStateException e) {
            log.info("JWT 토큰이 잘못되었습니다");
        }
        return false;
    }

    // Access Token 만료시 갱신때 사용할 정보를 얻기 위해 Claim 리턴
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}