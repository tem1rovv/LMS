package uz.pdp.lmsad.dto.auth;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.lmsad.entity.enums.RoleName;

@Getter
@Setter
public class RoleDto {
    private String id;
    private RoleName roleName;
}
