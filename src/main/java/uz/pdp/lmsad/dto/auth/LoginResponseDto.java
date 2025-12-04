package uz.pdp.lmsad.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpiresIn;
    private Date refreshTokenExpiresIn;
}
