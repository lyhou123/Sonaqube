package co.istad.project.security;

import co.istad.project.domain.User;
import co.istad.project.domain.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class UserDetail implements UserDetails {

    private User user;

    Set<Role> roles = new HashSet<>();

    //make authority in to this method
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return !user.isEnabled();
    }
}
