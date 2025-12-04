package uz.pdp.lmsad.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lmsad.entity.base.IdEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class AssignmentResponse extends IdEntity {

    @ManyToOne
    private AuthUser user;

    @ManyToOne
    private Assignment assignment;

    @Column(length = 2000)
    private String text;

}
