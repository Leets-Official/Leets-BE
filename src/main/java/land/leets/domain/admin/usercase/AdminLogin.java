package land.leets.domain.admin.usercase;


import land.leets.global.jwt.dto.JwtResponse;

public interface AdminLogin {
    JwtResponse execute(String id, String password);
}
