package uz.pdp.lmsad.dto.auth;


import lombok.Getter;
import lombok.Setter;
import uz.pdp.lmsad.entity.Role;

import java.util.Set;

@Getter
@Setter
public class UpdateAuthUserDto {
    private Set<String> roleIds;
}
