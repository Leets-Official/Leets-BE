package land.leets.domain.admin.usercase;

import land.leets.domain.admin.domain.Admin;
import land.leets.domain.admin.domain.repository.AdminRepository;
import land.leets.domain.admin.exception.AdminNotFoundException;
import land.leets.domain.shared.AuthRole;
import land.leets.domain.shared.exception.PasswordNotMatchException;
import land.leets.global.jwt.JwtProvider;
import land.leets.global.jwt.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminLoginImpl implements AdminLogin {
    
    private final JwtProvider jwtProvider;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse execute(String id, String password) {
        Admin admin = adminRepository.findById(id).orElseThrow(AdminNotFoundException::new);

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new PasswordNotMatchException();
        }

        String accessToken = this.jwtProvider.generateToken(admin.getUid(), admin.getId(), AuthRole.ROLE_ADMIN, false);
        String refreshToken = this.jwtProvider.generateToken(admin.getUid(), admin.getId(), AuthRole.ROLE_ADMIN, true);

        return new JwtResponse(accessToken, refreshToken);
    }
}
