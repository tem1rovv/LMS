package uz.pdp.lmsad.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.lmsad.dto.instructordashboard.CourseUserCount;
import uz.pdp.lmsad.dto.userdashboard.RatingDto;
import uz.pdp.lmsad.dto.userdashboard.ShowRatingDto;
import uz.pdp.lmsad.dto.userdashboard.UserCertificateCount;
import uz.pdp.lmsad.service.DashboardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/dashboard")
@PreAuthorize("hasRole('USER')")
@SecurityRequirement(name = "BearerAuth")
public class StudentDashboardController {


    private final DashboardService dashboardService;

    @GetMapping("/certificate/count")
    public UserCertificateCount getUserCertificateCount(){
        return dashboardService.getCertificateCount();
    }

    @GetMapping("/rating")
    public RatingDto getRating(){
        return dashboardService.getRating();
    }

    @GetMapping("/rating/see")
    public List<ShowRatingDto> showRating(){
        return dashboardService.showStudentRating();
    }

}
