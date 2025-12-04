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
public class Question extends IdEntity {

    private String title;


    @OneToOne
    private Option answer;


    @ManyToOne
    private Quiz quiz;


    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    @Builder.Default
    private List<Option> options = new ArrayList<>();
}
