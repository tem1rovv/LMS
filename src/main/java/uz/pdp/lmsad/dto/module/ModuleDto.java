package uz.pdp.lmsad.dto.module;


import lombok.Getter;
import lombok.Setter;
import uz.pdp.lmsad.dto.auth.AuthUserDto;
import uz.pdp.lmsad.dto.category.CategoryDto;
import uz.pdp.lmsad.dto.course.CourseDto;
import uz.pdp.lmsad.entity.enums.CourseStatus;

@Getter
@Setter
public class ModuleDto {

    private String id;
    private String name;
    private String courseId;
    private Integer orderIndex;
}
