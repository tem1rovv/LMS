package uz.pdp.lmsad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lmsad.entity.Role;
import uz.pdp.lmsad.entity.enums.RoleName;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,String> {

    Optional<Role> findByRoleName(RoleName roleName);


}
