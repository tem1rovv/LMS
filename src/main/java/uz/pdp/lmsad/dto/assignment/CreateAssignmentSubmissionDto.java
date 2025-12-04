package uz.pdp.lmsad.dto.assignment;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAssignmentSubmissionDto {
    private String userId;
    private String courseId;
    private Integer grade;
}
