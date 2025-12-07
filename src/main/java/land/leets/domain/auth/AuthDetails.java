package land.leets.domain.auth;

import land.leets.domain.shared.AuthRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class AuthDetails implements UserDetails {

    private final UUID uid;
    private final String email;
    private final AuthRole role;

    public AuthDetails(UUID uid, String email, AuthRole role) {
        this.uid = uid;
        this.email = email;
        this.role = role;
    }

    public UUID getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public AuthRole getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getRole()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
