package uz.pdp.lmsad.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lmsad.entity.base.BaseEntity;
import uz.pdp.lmsad.entity.enums.CourseStatus;

import java.util.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Course extends BaseEntity {

    private String title;
    private String description;
    private Double price;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    private AuthUser instructor;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    @Builder.Default
    private List<Module> modules = new ArrayList<>();
}
