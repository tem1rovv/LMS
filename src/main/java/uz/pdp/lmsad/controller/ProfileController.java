package uz.pdp.lmsad.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.lmsad.config.security.SessionUser;
import uz.pdp.lmsad.dto.admindashboard.CountInstructorDto;
import uz.pdp.lmsad.dto.admindashboard.CourseCountDto;
import uz.pdp.lmsad.dto.admindashboard.CourseRating;
import uz.pdp.lmsad.dto.auth.AuthUserDto;
import uz.pdp.lmsad.dto.profile.UpdateProfileDto;
import uz.pdp.lmsad.service.*;

import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "BearerAuth")
public class ProfileController {

    private final ProfileService profileService;
    private final RedisService redisService;
    private final EmailService emailService;
    private final SessionUser sessionUser;

    @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AuthUserDto updateProfile(
            @RequestParam(required = false) MultipartFile profileImage,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String phoneNumber
    ) {
        UpdateProfileDto dto = new UpdateProfileDto();
        dto.setProfileImage(profileImage);
        dto.setFullName(fullName);
        dto.setPhoneNumber(phoneNumber);
        return profileService.updateProfile(sessionUser.id(), dto);
    }


    //    @Cacheable("profile",)
    @GetMapping("/profile")
    public AuthUserDto profile() {
        return profileService.profile();
    }


    @PostMapping("/emailToSendCode")
    public ResponseEntity<?> sendCodeToEmail(@RequestParam String email) {
        String code = String.valueOf(new Random().nextInt(900000) + 100000);
        redisService.saveCode(email, code);

        emailService.sendEmail(email, "Email Verification Code",
                "Your verification code: " + code + "\n\nThis code will expire in 2 minutes.");

        return ResponseEntity.ok("Verification code sent to: " + email);
    }

    @PostMapping("/verifyEmailCode")
    public ResponseEntity<?> verifyCode(@RequestParam String email, @RequestParam String code) {
        String storedCode = redisService.getCode(email);

        if (storedCode == null)
            return ResponseEntity.badRequest().body("Code expired or not found.");

        if (!storedCode.equals(code))
            return ResponseEntity.badRequest().body("Invalid verification code.");

        redisService.deleteCode(email);
        return ResponseEntity.ok("Email successfully verified!");
    }
}
