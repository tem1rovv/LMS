package uz.pdp.lmsad.dto.course;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.lmsad.dto.auth.AuthUserDto;
import uz.pdp.lmsad.dto.category.CategoryDto;
import uz.pdp.lmsad.entity.enums.CourseStatus;

@Getter
@Setter
public class CourseDto {

    private String id;
    private String title;
    private String description;
    private Double price;
    private CategoryDto category;
    private CourseStatus status;
    private AuthUserDto instructor;
}
