package uz.pdp.lmsad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.lmsad.dto.userdashboard.ShowRatingDto;
import uz.pdp.lmsad.entity.Enrollment;

import java.util.List;


public interface EnrollmentRepository extends JpaRepository<Enrollment,String> {



    @Query("""
    select e from Enrollment e where e.user.id = :id and e.status in ('ACTIVE','COMPLETED') and e.isDelete = false
""")
    List<Enrollment> getAllEnrollmentOnlyActive(String id);



    @Query("""
       select e from Enrollment e where e.course.id=:courseId and e.user.id=:userId
""")
    Enrollment getEnrollmentByUserId(String courseId, String userId);


    @Query("""
       select count(1) from Enrollment e where e.course.instructor.id = :userId and e.course.id = :id
""")
    Integer getCourseUserCount(String id,String userId);


    @Query("""
       select count(1) from Enrollment e where e.user.id = :id and e.status = 'COMPLETED'
""")
    Integer getRatingByCompleteStatus(String id);


    @Query("""
        select new uz.pdp.lmsad.dto.userdashboard.ShowRatingDto(e.user.id, count(1)) from Enrollment e where e.status = 'COMPLETED' group by e.user.id order by count(1) desc
""")
    List<ShowRatingDto> getAllStudentRating();

}
