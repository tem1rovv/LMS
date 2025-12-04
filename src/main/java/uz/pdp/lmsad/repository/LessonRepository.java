package uz.pdp.lmsad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.lmsad.entity.Lesson;
import uz.pdp.lmsad.entity.Module;


public interface LessonRepository extends JpaRepository<Lesson, String> {



    @Query("""
    select count(1) from Lesson l where l.module.id = :id
""")
    Integer countLessonByModuleId(String id);
}
