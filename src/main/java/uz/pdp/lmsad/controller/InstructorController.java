package uz.pdp.lmsad.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.lmsad.dto.assignment.AssignmentDto;
import uz.pdp.lmsad.dto.assignment.CreateAssignmentDto;
import uz.pdp.lmsad.dto.assignment.CreateAssignmentSubmissionDto;
import uz.pdp.lmsad.dto.course.CourseDto;
import uz.pdp.lmsad.dto.course.CreateCourseDto;
import uz.pdp.lmsad.dto.course.UpdateCourseDto;
import uz.pdp.lmsad.dto.lesson.CreateLessonDto;
import uz.pdp.lmsad.dto.lesson.LessonDto;
import uz.pdp.lmsad.dto.lesson.UpdateLessonDto;
import uz.pdp.lmsad.dto.module.CreateModuleDto;
import uz.pdp.lmsad.dto.module.ModuleDto;
import uz.pdp.lmsad.dto.review.ReviewDto;
import uz.pdp.lmsad.service.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instructor")
@PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
@SecurityRequirement(name = "BearerAuth")
//@CrossOrigin(origins = "*")
public class InstructorController {


    private final CourseService courseService;
    private final ModuleService moduleService;
    private final LessonService lessonService;
    private final AssignmentService assignmentService;
    private final ReviewService reviewService;


    ///              -------------------- COURSE--------------------
    @PostMapping("/courses")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CreateCourseDto dto) {
        return ResponseEntity.status(201).body(courseService.create(dto));
    }

    @GetMapping("/courses")
    public List<CourseDto> getAllCourse() {
        return courseService.getAll();
    }


    @GetMapping("/courses/{id}")
    public CourseDto getAllCourse(@PathVariable String id) {
        return courseService.get(id);
    }

    @PutMapping("/courses/{id}")
    public CourseDto updateCourse(@RequestBody UpdateCourseDto dto, @PathVariable String id) {
        return courseService.update(id, dto);
    }


    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/courses/{id}/activate")
    public ResponseEntity<?> activateCourse(@PathVariable String id) {
        courseService.activateCourse(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/courses/{id}/inActivate")
    public ResponseEntity<?> inActivateCourse(@PathVariable String id) {
        courseService.inActivateCourse(id);
        return ResponseEntity.ok().build();
    }


    ///              -------------------- MODULE--------------------
    @PostMapping("/course/{id}/module")
    public ResponseEntity<ModuleDto> createModule(@RequestBody CreateModuleDto dto, @PathVariable String id) {
        dto.setCourseId(id);
        return ResponseEntity.status(201).body(moduleService.create(dto));
    }

    @GetMapping("/course/{id}/module")
    public List<ModuleDto> getAllModule(@PathVariable String id) {
        return moduleService.getAll(id);
    }

    @GetMapping("/course/module/{id}")
    public ModuleDto getModule(@PathVariable String id) {
        return moduleService.get(id);
    }

    @DeleteMapping("/course/module/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable String id) {
        moduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    ///              -------------------- LESSON --------------------



    @PostMapping(
            value = "/courses/modules/{moduleId}/lessons",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<LessonDto> createLesson(
            @RequestParam("content") MultipartFile content,
            @RequestParam("title") String title,
            @PathVariable String moduleId) {

        CreateLessonDto dto = new CreateLessonDto();
        dto.setModuleId(moduleId);
        dto.setContent(content);
        dto.setTitle(title);
        return ResponseEntity.status(201).body(lessonService.create(dto));
    }


    @GetMapping("/courses/modules/{id}/lessons")
    public List<LessonDto> getAllLessons(@PathVariable String id) {
        return lessonService.getAll(id);
    }


    @GetMapping("/courses/modules/{moduleId}/lessons/{lessonId}")
    public LessonDto getLessons(@PathVariable String lessonId, @PathVariable String moduleId) {
        return lessonService.get(moduleId, lessonId);
    }

    @PutMapping(value = "/courses/modules/{moduleId}/lessons/{lessonId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public LessonDto updateLesson(@PathVariable String lessonId,
                                  @PathVariable String moduleId,
                                  @RequestParam("title") String title,
                                  @RequestParam("module") String module,
                                  @RequestParam("content") MultipartFile content) {
        UpdateLessonDto dto =new UpdateLessonDto();
        dto.setTitle(title);
        dto.setContent(content);
        dto.setPathModuleId(moduleId);
        dto.setModuleId(module);
        return lessonService.update(lessonId, dto);
    }

    @DeleteMapping("/courses/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable String lessonId, @PathVariable String moduleId) {
        lessonService.delete(moduleId, lessonId);
        return ResponseEntity.noContent().build();
    }


    ///              -------------------- Assignment --------------------

    @PostMapping("/courses/modules/lessons/{id}/assignments")
    public ResponseEntity<AssignmentDto> createAssignment(@RequestBody CreateAssignmentDto dto, @PathVariable String id) {
        dto.setLessonId(id);
        return ResponseEntity.status(201).body(assignmentService.create(dto));
    }

    @GetMapping("/courses/modules/lessons/{id}/assignments")
    public List<AssignmentDto> getAllAssignment(@PathVariable String id) {
        return assignmentService.getAll(id);
    }


    @GetMapping("/courses/modules/lessons/{lessonId}/assignments/{assignmentId}")
    public AssignmentDto getAssignment(@PathVariable String assignmentId, @PathVariable String lessonId) {
        return assignmentService.get(lessonId, assignmentId);
    }


    @PostMapping("/courses/modules/lessons/assignment-submission/{id}")
    public ResponseEntity<?> sendAssignmentSubmission(@RequestBody CreateAssignmentSubmissionDto dto, @PathVariable String id) {
        assignmentService.createAssignmentSubmission(id, dto);
        return ResponseEntity.ok("Send assignment submission!");
    }


    @GetMapping("/courses/{id}/review")
    public List<ReviewDto> getReview(@PathVariable String id) {
        return reviewService.getAllReviewByCourse(id);
    }




}
