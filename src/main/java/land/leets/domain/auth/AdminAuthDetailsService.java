package land.leets.domain.auth;

import land.leets.domain.admin.domain.Admin;
import land.leets.domain.admin.domain.repository.AdminRepository;
import land.leets.domain.shared.AuthRole;
import land.leets.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminAuthDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public AuthDetails loadUserByUsername(String sub) throws UsernameNotFoundException {
        Admin admin = this.adminRepository
                .findById(sub)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.ADMIN_NOT_FOUND.getMessage()));
        return new AuthDetails(admin.getUid(), admin.getId(), AuthRole.ROLE_ADMIN);
    }
}
