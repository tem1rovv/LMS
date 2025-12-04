package uz.pdp.lmsad.config.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SessionUser {

    public UserDetails user() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
        if ( principal instanceof UserDetails ud )
            return ud;
        return null;
    }

    public String id() {
        UserDetails user = user();
        if ( Objects.isNull(user) )
            return null;
        return user.getId();
    }
}