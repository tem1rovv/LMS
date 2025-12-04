package uz.pdp.lmsad.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import uz.pdp.lmsad.entity.base.BaseEntity;
import uz.pdp.lmsad.entity.base.IdEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Category extends BaseEntity {

    private String name;
    @Builder.Default
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();
}
