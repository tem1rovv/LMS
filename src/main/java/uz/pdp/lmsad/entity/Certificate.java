package uz.pdp.lmsad.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.pdp.lmsad.entity.base.BaseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Certificate extends BaseEntity {


    @ManyToOne
    private AuthUser user;


    @OneToOne
    private Course course;

    private String certificateUrl;

}
