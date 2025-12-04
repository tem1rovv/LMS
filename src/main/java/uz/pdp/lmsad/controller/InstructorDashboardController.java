package uz.pdp.lmsad.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.lmsad.dto.admindashboard.CountInstructorDto;
import uz.pdp.lmsad.dto.admindashboard.CourseCountDto;
import uz.pdp.lmsad.dto.instructordashboard.CourseUserCount;
import uz.pdp.lmsad.service.CourseService;
import uz.pdp.lmsad.service.DashboardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instructor/dashboard")
@PreAuthorize("hasRole('INSTRUCTOR')")
@SecurityRequirement(name = "BearerAuth")
public class InstructorDashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/course/{id}/user-count")
    public CourseUserCount getCourseUserCount(@PathVariable String id){
        return dashboardService.getCourseUserCount(id);
    }

}
