package uz.pdp.lmsad.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
public class AuthUserDto {
    private String id;
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;
    private String imageUrl;
    private Set<RoleDto> roles;
}
