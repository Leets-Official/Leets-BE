package land.leets.domain.admin.usercase;

import io.jsonwebtoken.Claims;
import land.leets.domain.shared.AuthRole;
import land.leets.global.jwt.JwtProvider;
import land.leets.global.jwt.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdminRefreshTokenImpl implements AdminRefreshToken {
    private final JwtProvider jwtProvider;

    @Override
    public JwtResponse execute(String refreshToken) {

        jwtProvider.validateToken(refreshToken, true);
        Claims claims = jwtProvider.parseClaims(refreshToken, true);
        String role = claims.get("role", String.class);
        UUID uuid = UUID.fromString(claims.get("uid", String.class));
        String newAccessToken = jwtProvider.generateToken(uuid, claims.getSubject(), AuthRole.valueOf(role), false);
        return new JwtResponse(newAccessToken, null);
    }
}
