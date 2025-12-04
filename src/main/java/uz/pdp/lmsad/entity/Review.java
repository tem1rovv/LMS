package uz.pdp.lmsad.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.lmsad.entity.base.BaseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Review extends BaseEntity {


    @ManyToOne
    private AuthUser user;

    @ManyToOne
    private Course course;

    private Integer rating;

    private String text;
}
