package uz.pdp.lmsad.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.lmsad.config.jwt.JwtTokenUtil;
import uz.pdp.lmsad.dto.auth.*;
import uz.pdp.lmsad.entity.AuthUser;
import uz.pdp.lmsad.entity.Role;
import uz.pdp.lmsad.entity.enums.RoleName;
import uz.pdp.lmsad.mapper.AuthUserMapper;
import uz.pdp.lmsad.props.AppProps;
import uz.pdp.lmsad.repository.AuthUserRepository;
import uz.pdp.lmsad.repository.RoleRepository;
import uz.pdp.lmsad.validator.AuthUserValidator;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthService extends
        AbstractService<
                AuthUserRepository,
                AuthUserMapper,
                AuthUserValidator> implements CRUDService<AuthUserDto, CreateAuthUserDto, UpdateAuthUserDto, String> {


    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final AppProps appProps;

    public AuthService(AuthUserRepository repository, AuthUserMapper mapper, AuthUserValidator validator, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, AppProps appProps, AuthUserRepository authUserRepository) {
        super(repository, mapper, validator);
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.appProps = appProps;
    }

    @Override
    public AuthUserDto create(CreateAuthUserDto dto) {
        validator.validateOnCreate(dto);
        AuthUser authUser = mapper.toEntity(dto);
        Role role = roleRepository.findByRoleName(RoleName.USER).orElseThrow(
                () -> new RuntimeException("Role not Found")
        );
        authUser.setImageUrl("https://api.telegram.org/file/bot8501886143:AAHiFYRUoM-MmmaBh-UwNtUJrrUv7mJssXM/thumbnails/file_3.jpg");
        authUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        authUser.getRoles().add(role);
        return mapper.toDto(repository.save(authUser));
    }

    @Override
    public AuthUserDto update(String id, UpdateAuthUserDto dto) {
        AuthUser authUser = repository.findById(id).orElseThrow();

        Set<Role> role = new HashSet<>(roleRepository.findAllById(dto.getRoleIds()));
        authUser.setRoles(role);
        return mapper.toDto(repository.save(authUser));
    }

    @Override
    public AuthUserDto get(String id) {
        AuthUser authUser = repository.findById(id).orElseThrow();
        return mapper.toDto(authUser);
    }

    @Override
    public List<AuthUserDto> getAll() {
        return List.of();
    }


    @Override
    @Transactional
    public void delete(String id) {
        AuthUser authUser = repository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        repository.delete(id);
    }


    public LoginResponseDto login(LoginRequestDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        authenticationManager.authenticate(authenticationToken);

        String accessToken = jwtTokenUtil.generateAccessToken(dto.getUsername());
        String refreshToken = jwtTokenUtil.generateRefreshToken(dto.getUsername());
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setAccessToken(accessToken);
        loginResponseDto.setRefreshToken(refreshToken);
        loginResponseDto.setAccessTokenExpiresIn(new Date(System.currentTimeMillis() + appProps.getAccessTokenExpireTime()));
        loginResponseDto.setRefreshTokenExpiresIn(new Date(System.currentTimeMillis() + appProps.getRefreshTokenExpireTime()));
        return loginResponseDto;
    }


    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto dto) {
        String refreshToken = dto.getRefreshToken();
        if (!jwtTokenUtil.isValid(refreshToken)) {
            throw new RuntimeException("Refresh token is invalid or expired");
        }
        String username = jwtTokenUtil.getUsername(refreshToken);

        String newAccessToken = jwtTokenUtil.generateAccessToken(username);
        String newRefreshToken = jwtTokenUtil.generateRefreshToken(username);


        return RefreshTokenResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .accessTokenExpiresIn(new Date(System.currentTimeMillis() + (appProps.getAccessTokenExpireTime())))
                .refreshTokenExpiresIn(new Date(System.currentTimeMillis() + (appProps.getRefreshTokenExpireTime())))
                .build();
    }


    public Page<AuthUserDto> getAllUsers(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<AuthUser> users = repository.findAll(pageable);
        return users.map(mapper::toDto);
    }
}
