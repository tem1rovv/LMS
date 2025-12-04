package uz.pdp.lmsad;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import uz.pdp.lmsad.dto.auth.CreateAuthUserDto;
import uz.pdp.lmsad.entity.AuthUser;
import uz.pdp.lmsad.entity.Role;
import uz.pdp.lmsad.entity.enums.RoleName;
import uz.pdp.lmsad.repository.RoleRepository;
import uz.pdp.lmsad.service.AuthService;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
@EnableCaching
public class LmsAdApplication {





    public static void main(String[] args) {
        SpringApplication.run(LmsAdApplication.class, args);
    }


//    @Bean
    CommandLineRunner commandLineRunner(PasswordEncoder passwordEncoder, AuthService authService) {

        return args -> {
            CreateAuthUserDto createAuthUserDto = new CreateAuthUserDto();
            createAuthUserDto.setUsername("admin");
            createAuthUserDto.setPassword(passwordEncoder.encode("admin123"));
            createAuthUserDto.setFullName("admin");
            authService.create(createAuthUserDto);
        };
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()
                    || authentication.getPrincipal().equals("anonymousUser")) {
                return Optional.empty();
            }
            return Optional.of(authentication.getName());
        };
    }
    @Bean // Bu RestTemplate ni butun loyiha bo'ylab ishlatishga imkon beradi
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
