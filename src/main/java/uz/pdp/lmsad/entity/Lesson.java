package uz.pdp.lmsad.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lmsad.entity.base.BaseEntity;
import uz.pdp.lmsad.entity.enums.LessonType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Lesson extends BaseEntity {


    private String title;
    private String contentUrl;
    private Integer orderIndex;


    @Enumerated(value = EnumType.STRING)
    private LessonType lessonType;

    @ManyToOne
    private Module module;


    @Builder.Default
    @OneToMany(mappedBy = "lesson",cascade = CascadeType.ALL)
    private List<Assignment> assignments = new ArrayList<>();
}
