package uz.pdp.lmsad.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.lmsad.entity.base.IdEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class AssignmentSubmission extends IdEntity {


    @ManyToOne
    private AuthUser user;

    @ManyToOne
    private Assignment assignment;

    private Integer grade;

    private String feedback;
}
