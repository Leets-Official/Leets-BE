package land.leets.domain.admin.usercase;


import land.leets.global.jwt.dto.JwtResponse;

public interface AdminRefreshToken {
    JwtResponse execute(String refreshToken);
}
