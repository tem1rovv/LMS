package uz.pdp.lmsad.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.lmsad.config.security.SessionUser;
import uz.pdp.lmsad.dto.admindashboard.CountInstructorDto;
import uz.pdp.lmsad.dto.admindashboard.CourseRating;
import uz.pdp.lmsad.dto.auth.AuthUserDto;
import uz.pdp.lmsad.dto.instructordashboard.CourseUserCount;
import uz.pdp.lmsad.dto.profile.UpdateProfileDto;
import uz.pdp.lmsad.dto.userdashboard.RatingDto;
import uz.pdp.lmsad.dto.userdashboard.ShowRatingDto;
import uz.pdp.lmsad.dto.userdashboard.UserCertificateCount;
import uz.pdp.lmsad.entity.AuthUser;
import uz.pdp.lmsad.entity.Course;
import uz.pdp.lmsad.mapper.AuthUserMapper;
import uz.pdp.lmsad.repository.*;
import uz.pdp.lmsad.util.TelegramService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {


    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TelegramService telegramService;
    private final AuthUserMapper authUserMapper;
    private final SessionUser sessionUser;

    public AuthUserDto updateProfile(String id, UpdateProfileDto dto) {
        AuthUser authUser = authUserRepository.findById(id).orElseThrow();
        if (dto.getFullName() != null) {
            authUser.setFullName(dto.getFullName());
        }
        if (dto.getPhoneNumber() != null) {
            authUser.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getProfileImage() != null) {
            String imageUrl = telegramService.uploadFileToTelegram(dto.getProfileImage());
            authUser.setImageUrl(imageUrl);
        }
        return authUserMapper.toDto(authUserRepository.save(authUser));
    }


    public AuthUserDto profile() {
        AuthUser authUser = authUserRepository.findById(sessionUser.id()).orElseThrow();
        return authUserMapper.toDto(authUser);
    }
}
