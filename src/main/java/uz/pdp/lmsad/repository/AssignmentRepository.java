package uz.pdp.lmsad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.lmsad.entity.Assignment;
import uz.pdp.lmsad.entity.Lesson;


public interface AssignmentRepository extends JpaRepository<Assignment, String> {


    @Query("""
      select sum(asn.grade) from AssignmentSubmission asn where asn.assignment.lesson.id = :id and asn.user.id = :userId
""")
    Integer getGradeByLessonId(String id, String userId);

}
