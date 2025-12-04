package uz.pdp.lmsad.repository;

import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.lmsad.dto.course.CourseDto;
import uz.pdp.lmsad.entity.Course;

import java.util.List;
import java.util.Optional;


public interface CourseRepository extends JpaRepository<Course,String>, JpaSpecificationExecutor<Course> {


    @Query("""
       select c from Course c where c.instructor.id = :id and c.isDelete = false
""")
    List<Course> findAllByInstructorId(String id);

    @Modifying
    @Query("""
      update Course c set c.isDelete = true where c.id=:id
""")
    void delete(String id);


    @Query("""
        select c from Course c where c.id = :id and c.isDelete = false
""")
    Optional<Course> findById(String id);




    @Query("""
                select c from Course c where c.status='ACTIVE' and c.isDelete = false
            """)
    List<Course> getAllCourseOnlyActive();



    @Query("""
    select count(1) from Course c where c.isDelete = false and c.status = 'ACTIVE'
""")
    Integer getCourseCount();



}
