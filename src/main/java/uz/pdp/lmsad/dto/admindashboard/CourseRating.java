package uz.pdp.lmsad.dto.admindashboard;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
public class CourseRating {

    private String courseId;
    private String courseName;
    private String instructorId;
    private Long rating;


}
