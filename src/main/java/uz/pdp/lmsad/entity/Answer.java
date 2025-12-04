package uz.pdp.lmsad.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.pdp.lmsad.entity.base.IdEntity;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Answer extends IdEntity {


    @OneToOne
    private Question question;


    @OneToOne
    private Option selectedOption;

    @ManyToOne
    private Submission submission;

    private boolean isCorrect;
}
