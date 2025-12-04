package uz.pdp.lmsad.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lmsad.entity.base.IdEntity;
import uz.pdp.lmsad.entity.enums.RoleName;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends IdEntity {

    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
