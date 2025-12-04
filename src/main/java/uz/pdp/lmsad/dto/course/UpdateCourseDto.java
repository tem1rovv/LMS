package uz.pdp.lmsad.dto.course;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.lmsad.entity.enums.CourseStatus;

@Getter
@Setter
public class UpdateCourseDto {

    private String title;
    private String description;
    private Double price;
    private CourseStatus status;
    private String categoryId;
}
