package uz.pdp.lmsad.dto.auth;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class RefreshTokenRequestDto {
    private String refreshToken;
}
