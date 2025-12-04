package uz.pdp.lmsad.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.lmsad.config.security.SessionUser;
import uz.pdp.lmsad.dto.admindashboard.CountInstructorDto;
import uz.pdp.lmsad.dto.admindashboard.CourseRating;
import uz.pdp.lmsad.dto.instructordashboard.CourseUserCount;
import uz.pdp.lmsad.dto.userdashboard.RatingDto;
import uz.pdp.lmsad.dto.userdashboard.ShowRatingDto;
import uz.pdp.lmsad.dto.userdashboard.UserCertificateCount;
import uz.pdp.lmsad.entity.Course;
import uz.pdp.lmsad.repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {


    private final AuthUserRepository authUserRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final SessionUser sessionUser;
    private final CertificateRepository certificateRepository;
    private final ReviewRepository reviewRepository;

    public CountInstructorDto getInstructorCount() {
        Integer count = authUserRepository.getInstructorCount();
        CountInstructorDto countInstructorDto = new CountInstructorDto();
        countInstructorDto.setCount(count);
        return countInstructorDto;
    }

    public CourseUserCount getCourseUserCount(String id) {
        Course course = courseRepository.findById(id).orElseThrow();
        Integer count = enrollmentRepository.getCourseUserCount(id,sessionUser.id());
        CourseUserCount courseUserCount = new CourseUserCount();
        courseUserCount.setCount(count);
        return courseUserCount;
    }

    public UserCertificateCount getCertificateCount() {
        Integer count = certificateRepository.getCountByUserId(sessionUser.id());
        UserCertificateCount userCertificateCount = new UserCertificateCount();
        userCertificateCount.setCount(count);
        return userCertificateCount;
    }

    public RatingDto getRating() {
        Integer number = enrollmentRepository.getRatingByCompleteStatus(sessionUser.id());
        RatingDto  ratingDto = new RatingDto();
        ratingDto.setNumber(number);
        return ratingDto;
    }

    public List<CourseRating> showCourseRating() {
        return reviewRepository.getCourseRating();
    }

    public List<ShowRatingDto> showStudentRating() {
        return enrollmentRepository.getAllStudentRating();
    }
}
