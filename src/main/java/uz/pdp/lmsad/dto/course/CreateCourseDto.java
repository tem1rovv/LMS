package uz.pdp.lmsad.dto.course;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCourseDto {

    private String title;
    private String description;
    private Double price;
    private String categoryId;
}
