package land.leets.global.jwt;

import io.jsonwebtoken.*;
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {
    private final String accessSecret;
    private final String refreshSecret;
    private final UserAuthDetailsService userAuthDetailsService;
    private final AdminAuthDetailsService adminAuthDetailsService;

    @Autowired
    public JwtProvider(@Value("${jwt.auth.access_secret}") String accessSecret,
                       @Value("${jwt.auth.refresh_secret}") String refreshSecret,
                       UserAuthDetailsService userAuthDetailsService,
                       AdminAuthDetailsService adminAuthDetailsService) {
        this.accessSecret = accessSecret;
        this.refreshSecret = refreshSecret;
        this.userAuthDetailsService = userAuthDetailsService;
        this.adminAuthDetailsService = adminAuthDetailsService;
    }

    public String generateToken(UUID uuid, String sub, AuthRole role, boolean isRefreshToken) {
        Instant accessDate = LocalDateTime.now().plusHours(6).atZone(ZoneId.systemDefault()).toInstant();
        Instant refreshDate = LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant();

        return Jwts.builder()
                .claim("role", role.getRole())
                .claim("uid", uuid.toString())
                .setSubject(sub)
                .setExpiration(isRefreshToken ? Date.from(refreshDate) : Date.from(accessDate))
                .signWith(SignatureAlgorithm.HS256, isRefreshToken ? refreshSecret : accessSecret)
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
        } catch (SignatureException | UnsupportedJwtException | IllegalArgumentException | MalformedJwtException e) {
            throw new InvalidTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        }
    }

    public Claims parseClaims(String token, boolean isRefreshToken) {
        try {
            JwtParser parser = Jwts.parser().setSigningKey(isRefreshToken ? refreshSecret : accessSecret);
            return parser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
