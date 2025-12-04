package uz.pdp.lmsad.dto.userdashboard;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShowRatingDto {
    private String userId;
    private Long completeCourseCount;
}
