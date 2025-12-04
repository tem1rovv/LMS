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
public class Assignment extends IdEntity {

    @ManyToOne
    private Lesson lesson;


    @Column(length = 2000)
    private String text;


    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private List<AssignmentSubmission> assignmentSubmissions = new ArrayList<>();
}
