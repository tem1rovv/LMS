package uz.pdp.lmsad.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.lmsad.dto.assignment.AssignmentDto;
import uz.pdp.lmsad.util.AiGraderClient;
import uz.pdp.lmsad.config.security.SessionUser;
import uz.pdp.lmsad.dto.CertificateDto;
import uz.pdp.lmsad.dto.assignment.GradingResultDto;
import uz.pdp.lmsad.dto.course.CourseDto;
import uz.pdp.lmsad.dto.enroll.EnrollmentDto;
import com.lowagie.text.*;
import uz.pdp.lmsad.dto.review.CreateReviewDto;
import uz.pdp.lmsad.dto.review.ReviewDto;
import uz.pdp.lmsad.entity.*;
import uz.pdp.lmsad.entity.enums.EnrollmentStatus;
import uz.pdp.lmsad.mapper.*;
import uz.pdp.lmsad.repository.*;
import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import uz.pdp.lmsad.util.TelegramService;
import uz.pdp.lmsad.validator.ReviewValidator;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final SessionUser sessionUser;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final TelegramService telegramService;
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final ReviewValidator reviewValidator;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentResponseRepository assignmentResponseRepository;
    private final AssignmentResponseMapper assignmentResponseMapper;
    private final AiGraderClient aiGraderClient;
    private final AssignmentSubmissionRepository assignmentSubmissionRepository;
    private final AssignmentMapper assignmentMapper;

    @Cacheable("courses")
    public List<CourseDto> getAllCourse() {
        List<Course> courses = courseRepository.getAllCourseOnlyActive();
        return courseMapper.toDtoList(courses);
    }

    @CacheEvict(cacheNames = "myCourses",allEntries = true)
    public EnrollmentDto enroll(String id) {
        Course course = courseRepository.findById(id).orElseThrow();
        Enrollment enrollment = Enrollment
                .builder()
                .user(sessionUser.user().getAuthUser())
                .course(course)
                .status(EnrollmentStatus.ACTIVE)
                .progress(0)
                .build();
        return enrollmentMapper.toDto(enrollmentRepository.save(enrollment));
    }

    @Cacheable("myCourses")
    public List<EnrollmentDto> getAllEnrollment() {
        List<Enrollment> enrollments = enrollmentRepository.getAllEnrollmentOnlyActive(sessionUser.id());
        return enrollmentMapper.toDtoList(enrollments);
    }


    @Transactional
    public void finishLesson(String id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow();
        Integer progress = assignmentRepository.getGradeByLessonId(id,sessionUser.id());
        Enrollment enrollment = enrollmentRepository.getEnrollmentByUserId(lesson.getModule().getCourse().getId(),sessionUser.id());
        if (progress != null){
            enrollment.setProgress(enrollment.getProgress()+progress);
        }
        enrollmentRepository.save(enrollment);
    }

    public Integer getProgress(String id) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow();
        return enrollment.getProgress();
    }


    public Enrollment completeCourse(String id) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow();
        enrollment.setStatus(EnrollmentStatus.COMPLETED);

        if (enrollment.getProgress() != 100) {
            throw new RuntimeException("Your don't complete your course yet!");
        }
        return enrollmentRepository.save(enrollment);
    }

    public byte[] getCertificate(String id) {
        Enrollment enrollment = completeCourse(id);
        byte[] bytes = generateCertificate(sessionUser.user().getUsername(), enrollment.getCourse().getTitle());
        String certificateUrl = telegramService.uploadBytesToTelegram(bytes, enrollment.getCourse().getTitle());
        Certificate certificate = Certificate
                .builder()
                .user(sessionUser.user().getAuthUser())
                .course(enrollment.getCourse())
                .certificateUrl(certificateUrl)
                .build();
        certificateRepository.save(certificate);
        return bytes;
    }

    public byte[] generateCertificate(String fullName, String courseTitle) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
            Paragraph title = new Paragraph("Certificate of Completion", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // Name
            Font nameFont = FontFactory.getFont(FontFactory.HELVETICA, 18);
            Paragraph name = new Paragraph("This is to certify that " + fullName, nameFont);
            name.setAlignment(Element.ALIGN_CENTER);
            document.add(name);

            document.add(Chunk.NEWLINE);

            // Course
            Paragraph course = new Paragraph("has successfully completed the course \"" + courseTitle + "\"", nameFont);
            course.setAlignment(Element.ALIGN_CENTER);
            document.add(course);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // Footer
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12);
            Paragraph footer = new Paragraph("Issued by LMS Platform", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating certificate", e);
        }
    }

    public List<CertificateDto> showCertificate() {
        List<Certificate> certificates = certificateRepository.findCertificateByUserId(sessionUser.id());
        return certificateMapper.toDtoList(certificates);
    }


    public GradingResultDto createAssignmentResponse(String id, String assignmentResponse) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow();
        AssignmentResponse response = AssignmentResponse
                .builder()
                .user(sessionUser.user().getAuthUser())
                .assignment(assignment)
                .text(assignmentResponse)
                .build();
        assignmentResponseRepository.save(response);
        // 4. call AI to grade
        GradingResultDto grading = aiGraderClient.gradeAssignment(assignment.getText(), assignmentResponse);

        // 5. save AssignmentSubmission (record grade)
        AssignmentSubmission submission = AssignmentSubmission.builder()
                .assignment(assignment)
                .user(sessionUser.user().getAuthUser())
                .grade(grading.getGrade())
                .feedback(grading.getFeedback())
                .build();
        assignmentSubmissionRepository.save(submission);

        // 6. (optional) attach submission to assignment entity list if needed
        // assignment.getAssignmentSubmissions().add(submission);
        // assignmentRepo.save(assignment);

        return grading;
    }



    @Transactional
    public List<AssignmentDto> getAssignmentsByLessonId(String id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow();
        List<Assignment> assignments = lesson.getAssignments();
        return assignmentMapper.toDtoList(assignments);
    }
}
