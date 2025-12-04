package uz.pdp.lmsad.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lmsad.dto.admindashboard.CountInstructorDto;
import uz.pdp.lmsad.dto.admindashboard.CourseCountDto;
import uz.pdp.lmsad.dto.admindashboard.CourseRating;
import uz.pdp.lmsad.dto.auth.AuthUserDto;
import uz.pdp.lmsad.dto.auth.UpdateAuthUserDto;
import uz.pdp.lmsad.service.AuthService;
import uz.pdp.lmsad.service.CourseService;
import uz.pdp.lmsad.service.DashboardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/dashboard")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "BearerAuth")
public class AdminDashboardController {

    private final CourseService courseService;
    private final DashboardService dashboardService;

    @GetMapping("/course/count")
    public CourseCountDto getCourseCount(){
        return courseService.getActiveCourseCount();
    }
    @GetMapping("/instructor/count")
    public CountInstructorDto getInstructorCount(){
        return dashboardService.getInstructorCount();
    }


    @GetMapping("/courses/rating")
    public List<CourseRating> showCourseRating(){
        return dashboardService.showCourseRating();
    }
}
