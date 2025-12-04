package uz.pdp.lmsad.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.lmsad.dto.auth.*;
import uz.pdp.lmsad.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;




    @PostMapping("/register")
    public ResponseEntity<AuthUserDto> register(@RequestBody CreateAuthUserDto dto) {
        return ResponseEntity.status(201).body(authService.create(dto));
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto dto) {
        return authService.login(dto);
    }


    @PostMapping("/refresh-token")
    public RefreshTokenResponseDto refreshToken(@RequestBody RefreshTokenRequestDto dto) {
        return authService.refreshToken(dto);
    }
}
