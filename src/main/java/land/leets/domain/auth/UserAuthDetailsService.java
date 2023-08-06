package land.leets.domain.auth;

import land.leets.domain.shared.AuthRole;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.domain.repository.UserRepository;
import land.leets.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAuthDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public AuthDetails loadUserByUsername(String sub) throws UsernameNotFoundException {
        User user = this.userRepository
                .findBySub(sub)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));
        return new AuthDetails(user.getUid(), user.getSub(), AuthRole.ROLE_USER);
    }
}
