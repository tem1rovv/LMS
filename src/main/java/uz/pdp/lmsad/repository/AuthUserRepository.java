package uz.pdp.lmsad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.lmsad.entity.AuthUser;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser,String> {


    Optional<AuthUser> findByUsername(String username);


    boolean existsByUsername(String username);

    @Modifying
    @Query("""
        update AuthUser a set a.isDelete = true where a.id = :id
""")
    void delete(String id);

    @Query("""
    select count(a)
    from AuthUser a join a.roles r
    where r.roleName = 'INSTRUCTOR' and a.isDelete = false
""")
    Integer getInstructorCount();

}
