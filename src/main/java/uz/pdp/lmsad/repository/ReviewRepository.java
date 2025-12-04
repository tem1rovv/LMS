package uz.pdp.lmsad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.lmsad.dto.admindashboard.CourseRating;
import uz.pdp.lmsad.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {


    @Query("""
        select r from Review r where r.course.id = :id
""")
    List<Review> findAllReviewByCourseId(String id);


    @Query("""
    select new uz.pdp.lmsad.dto.admindashboard.CourseRating(
        r.course.id,
        r.course.title,
        r.course.instructor.id,
        sum(r.rating)
    )
    from Review r
    group by r.course.id, r.course.title, r.course.instructor.id
    order by sum(r.rating) desc
""")
    List<CourseRating> getCourseRating();

}