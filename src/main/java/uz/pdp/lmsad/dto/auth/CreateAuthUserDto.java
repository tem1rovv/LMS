package uz.pdp.lmsad.dto.auth;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAuthUserDto {
    private String fullName;
    private String username;
    private String password;
}
