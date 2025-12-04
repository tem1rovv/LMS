package uz.pdp.lmsad.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import uz.pdp.lmsad.entity.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Module extends BaseEntity {

    private  String name;


    private Integer orderIndex;

    @ManyToOne(cascade = CascadeType.ALL)
    private Course course;

    @OneToMany(mappedBy = "module",cascade = CascadeType.ALL)
    @Builder.Default
    private List<Lesson> lessons = new ArrayList<>();
}
