package uz.pdp.lmsad.entity;




import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lmsad.entity.base.IdEntity;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Submission extends IdEntity{

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    private AuthUser user;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL)
    private List<Answer> answers;

    private Integer score;
}
