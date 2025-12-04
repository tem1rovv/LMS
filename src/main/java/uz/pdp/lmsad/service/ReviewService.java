package uz.pdp.lmsad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.lmsad.config.security.SessionUser;
import uz.pdp.lmsad.dto.review.CreateReviewDto;
import uz.pdp.lmsad.dto.review.ReviewDto;
import uz.pdp.lmsad.entity.Course;
import uz.pdp.lmsad.entity.Review;
import uz.pdp.lmsad.mapper.ReviewMapper;
import uz.pdp.lmsad.repository.CourseRepository;
import uz.pdp.lmsad.repository.ReviewRepository;
import uz.pdp.lmsad.validator.ReviewValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {


    private final ReviewValidator reviewValidator;
    private final CourseRepository courseRepository;
    private final SessionUser sessionUser;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewDto createReview(String id, CreateReviewDto dto) {
        reviewValidator.validateOnCreate(dto);
        Course course = courseRepository.findById(id).orElseThrow();
        Review review = Review
                .builder()
                .course(course)
                .user(sessionUser.user().getAuthUser())
                .text(dto.getText())
                .rating(dto.getRating())
                .build();
        Review saved = reviewRepository.save(review);
        return reviewMapper.toDto(saved);
    }

    public List<ReviewDto> getAllReviewByCourse(String id) {
        Course course = courseRepository.findById(id).orElseThrow();
        List<Review> reviews = reviewRepository.findAllReviewByCourseId(id);
        return reviewMapper.toDtoList(reviews);
    }
}
