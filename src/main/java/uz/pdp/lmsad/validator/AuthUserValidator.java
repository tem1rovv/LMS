package uz.pdp.lmsad.validator;


import org.springframework.stereotype.Component;
import uz.pdp.lmsad.dto.auth.CreateAuthUserDto;
import uz.pdp.lmsad.repository.AuthUserRepository;

@Component
public class AuthUserValidator {
    private final AuthUserRepository authUserRepository;

    public AuthUserValidator(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    public void validateOnCreate(CreateAuthUserDto dto) {
        String username = dto.getUsername();
        if (authUserRepository.existsByUsername(username)) {
            throw new RuntimeException("Username is already in use");
        }
    }
}
