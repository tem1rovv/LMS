package uz.pdp.lmsad.dto.assignment;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentResponseDto {
    private String text;
    private String userId;
    private String assignmentId;
}
