package uz.pdp.lmsad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.lmsad.entity.Module;


public interface ModuleRepository extends JpaRepository<Module, String> {


    @Query("""
                   select count(1) from Module m where m.course.id = :id
            """)
    int countModuleByCourseId(String id);


    @Modifying
    @Query("""
        update Module m set m.isDelete = true where m.id = :id
""")
    void delete(String id);
}
