package uz.pdp.lmsad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.lmsad.entity.Category;


public interface CategoryRepository extends JpaRepository<Category,String> {



    @Modifying
    @Query("""
       update Category c set c.isDelete = true where c.id=:id
""")
    void delete(String id);

}
