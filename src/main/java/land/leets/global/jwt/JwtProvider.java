package land.leets.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import land.leets.domain.auth.AdminAuthDetailsService;
import land.leets.domain.auth.AuthDetails;
import land.leets.domain.auth.UserAuthDetailsService;
import land.leets.domain.shared.AuthRole;
import land.leets.global.jwt.exception.ExpiredTokenException;
import land.leets.global.jwt.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {
    private final SecretKey accessSecret;
    private final SecretKey refreshSecret;
    private final UserAuthDetailsService userAuthDetailsService;
    private final AdminAuthDetailsService adminAuthDetailsService;

    @Autowired
    public JwtProvider(@Value("${jwt.auth.access_secret}") String accessSecret,
                       @Value("${jwt.auth.refresh_secret}") String refreshSecret,
                       UserAuthDetailsService userAuthDetailsService,
                       AdminAuthDetailsService adminAuthDetailsService) {
        this.accessSecret = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
        this.refreshSecret = Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));
        this.userAuthDetailsService = userAuthDetailsService;
        this.adminAuthDetailsService = adminAuthDetailsService;
    }

    public String generateToken(UUID uuid, String sub, AuthRole role, boolean isRefreshToken) {
        Instant accessDate = LocalDateTime.now().plusHours(6).atZone(ZoneId.systemDefault()).toInstant();
        Instant refreshDate = LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant();

        return Jwts.builder()
                .claim("role", role.getRole())
                .claim("id" , uuid.toString())
                .subject(sub)
                .expiration(isRefreshToken ? Date.from(refreshDate) : Date.from(accessDate))
                .signWith(isRefreshToken ? refreshSecret : accessSecret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token, false);
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(claims.get("role").toString()));
        return new UsernamePasswordAuthenticationToken(getDetails(claims), "", authorities);
    }

    private AuthDetails getDetails(Claims claims) {
        if (claims.get("role").equals(AuthRole.ROLE_ADMIN.getRole())) {
            return this.adminAuthDetailsService.loadUserByUsername(claims.getSubject());
        }
        return this.userAuthDetailsService.loadUserByUsername(claims.getSubject());
    }

    public void validateToken(String token, boolean isRefreshToken) {
        try {
            parseClaims(token, isRefreshToken);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        }
    }

    public Claims parseClaims(String token, boolean isRefreshToken) {
        try {
            return Jwts.parser()
                    .verifyWith(isRefreshToken ? refreshSecret : accessSecret)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
