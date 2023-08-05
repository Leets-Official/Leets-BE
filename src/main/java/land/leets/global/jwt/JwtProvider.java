package land.leets.global.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletResponse;
import land.leets.domain.admin.domain.Admin;
import land.leets.domain.admin.domain.repository.AdminRepository;
import land.leets.domain.admin.exception.AdminNotFoundException;
import land.leets.domain.shared.AuthRole;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.domain.repository.UserRepository;
import land.leets.domain.user.exception.UserNotFoundException;
import land.leets.global.auth.CustomAdminDetails;
import land.leets.global.auth.CustomUserDetails;
import land.leets.global.jwt.exception.ExpiredTokenException;
import land.leets.global.jwt.exception.InvalidTokenException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Log4j2
@Component
public class JwtProvider {

    private final String ACCESS_SECRET;
    private final String REFRESH_SECRET;
    private static String COOKIE_REFRESH_TOKEN_KEY;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 7;	// 1week
    private final String AUTHORITIES_KEY = "role";

    @Autowired
    public JwtProvider(@Value("${jwt.auth.access_secret}") String accessSecret,
                       @Value("${jwt.auth.refresh_secret}") String refreshSecret,
                       @Value("${jwt.auth.cookie_key}") String cookieKey,
                       UserRepository userRepository,
                       AdminRepository adminRepository) {
        this.ACCESS_SECRET = accessSecret;
        this.REFRESH_SECRET = refreshSecret;
        COOKIE_REFRESH_TOKEN_KEY = cookieKey;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    public String generateAccessToken(Authentication authentication) {

        Instant accessDate = LocalDateTime.now().plusHours(6).atZone(ZoneId.systemDefault()).toInstant();

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(user.getName())
                .claim(AUTHORITIES_KEY, AuthRole.ROLE_USER.getRole())
                .setExpiration(Date.from(accessDate))
                .signWith(SignatureAlgorithm.HS256, ACCESS_SECRET)
                .compact();
    }

    public void generateRefreshToken(Authentication authentication, HttpServletResponse response) {

        Instant refreshDate = LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant();

        String refreshToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, REFRESH_SECRET)
                .setExpiration(Date.from(refreshDate))
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
        Claims claims = parseClaims(token, false);
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(claims.get(AUTHORITIES_KEY).toString()));

        UserDetails principal = getDetails(claims);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private UserDetails getDetails(Claims claims) {
        String id = claims.getSubject();
        if (claims.get(AUTHORITIES_KEY).equals(AuthRole.ROLE_USER.getRole())) {
            User user = userRepository.findById(Long.valueOf(id)).orElseThrow(UserNotFoundException::new);
            return new CustomUserDetails(user.getId(), user.getEmail(), AuthRole.ROLE_USER);
        }
        Admin admin = adminRepository.findById(Long.valueOf(id)).orElseThrow(AdminNotFoundException::new);
        return new CustomAdminDetails(admin.getUuid(), admin.getEmail(), AuthRole.ROLE_ADMIN);
    }

    public Boolean validateToken(String token, boolean isRefreshToken) {
        try {
            parseClaims(token, isRefreshToken);
            return true;
        } catch (SignatureException | UnsupportedJwtException | IllegalArgumentException | MalformedJwtException e) {
            throw new InvalidTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        }
    }

    // 토큰 정보를 검증하는 메서드
    // Access Token 만료시 갱신때 사용할 정보를 얻기 위해 Claim 리턴
    private Claims parseClaims(String token, boolean isRefreshToken) {
        try {
            JwtParser parser = Jwts.parser().setSigningKey(isRefreshToken ? REFRESH_SECRET : ACCESS_SECRET);
            return parser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}