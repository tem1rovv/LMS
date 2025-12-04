package uz.pdp.lmsad.config.security;


import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uz.pdp.lmsad.entity.AuthUser;

import java.util.Collection;

@Getter
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final String id;
    private final AuthUser authUser;

    public UserDetails(@NonNull AuthUser authUser) {
        this.authUser = authUser;
        this.id = authUser.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authUser
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName().name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getUsername();
    }


}
