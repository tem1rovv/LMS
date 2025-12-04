package uz.pdp.lmsad.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lmsad.dto.CertificateDto;
import uz.pdp.lmsad.dto.assignment.AssignmentDto;
import uz.pdp.lmsad.dto.assignment.GradingResultDto;
import uz.pdp.lmsad.dto.course.CourseDto;
import uz.pdp.lmsad.dto.enroll.EnrollmentDto;
import uz.pdp.lmsad.dto.lesson.LessonDto;
import uz.pdp.lmsad.dto.module.ModuleDto;
import uz.pdp.lmsad.dto.review.CreateReviewDto;
import uz.pdp.lmsad.dto.review.ReviewDto;
import uz.pdp.lmsad.dto.search.RequestSearchDto;
import uz.pdp.lmsad.entity.Course;
import uz.pdp.lmsad.service.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAnyRole('USER')")
@SecurityRequirement(name = "BearerAuth")
//@CrossOrigin(origins = "*")
public class StudentController {


    private final StudentService studentService;
    private final ModuleService moduleService;
    private final LessonService lessonService;
    private final ReviewService reviewService;
    private final AuthService authService;
    private final CourseService courseService;


    @PostMapping("/my-course/{id}/review")
    public ResponseEntity<ReviewDto> review(@PathVariable String id, CreateReviewDto dto) {
        return ResponseEntity.status(201).body(reviewService.createReview(id, dto));
    }

    @GetMapping("/courses")
    public List<CourseDto> getAllCourse() {
        return studentService.getAllCourse();
    }


    @PostMapping("/courses/{id}/enroll")
    public EnrollmentDto enroll(@PathVariable String id) {
        return studentService.enroll(id);
    }

    @GetMapping("/my-courses")
    public List<EnrollmentDto> getAllMyCourse() {
        return studentService.getAllEnrollment();
    }

    @GetMapping("/my-course/{id}/modules")
    public List<ModuleDto> getAllModules(@PathVariable String id) {
        return moduleService.getAll(id);
    }

    @GetMapping("/my-course/modules/{id}/lessons")
    public List<LessonDto> getAllLessons(@PathVariable String id) {
        return lessonService.getAll(id);
    }


    @GetMapping("/lesson/{id}/finish")
    public ResponseEntity<?> finishLesson(@PathVariable String id) {
        studentService.finishLesson(id);
        return ResponseEntity.ok("you finished this lesson");
    }


//    @GetMapping("/my-course/{enrollmentId}/complete")
//    public ResponseEntity<?> completeCourse(@PathVariable String enrollmentId){
//        studentService.completeCourse(enrollmentId);
//        return ResponseEntity.ok("You completed this course");
//    }

    @GetMapping("/my-courses/{enrollmentId}/complete")
    public ResponseEntity<?> getCertificate(@PathVariable String enrollmentId) {
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=certificate_" + enrollmentId + ".pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(studentService.getCertificate(enrollmentId));
    }


    @GetMapping("/my-course/{enrollmentId}/progress")
    public ResponseEntity<?> getProgress(@PathVariable String enrollmentId) {
        Integer progress = studentService.getProgress(enrollmentId);
        return ResponseEntity.ok("Your progress: %s".formatted(progress));
    }


    @GetMapping("/certificate")
    public List<CertificateDto> showCertificate() {
        return studentService.showCertificate();
    }


    @PostMapping("/assignments/{id}/response")
    public GradingResultDto assignmentResponse(@RequestBody String assignmentResponse, @PathVariable String id) {
        return studentService.createAssignmentResponse(id, assignmentResponse);
    }

    @PostMapping("/lessons/{id}/assignments")
    public List<AssignmentDto> getAllAssignment(@PathVariable String id) {
        return studentService.getAssignmentsByLessonId(id);
    }



    @GetMapping("/courses/search")
    public List<CourseDto> search(
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice){
        return courseService.filter(courseName,minPrice,maxPrice);
    }
}
