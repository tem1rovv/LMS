package uz.pdp.lmsad.dto.enroll;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.lmsad.dto.auth.AuthUserDto;
import uz.pdp.lmsad.dto.course.CourseDto;
import uz.pdp.lmsad.entity.enums.EnrollmentStatus;

@Getter
@Setter
public class EnrollmentDto {


    private String id;
    private CourseDto course;
    private Integer progress;
    private EnrollmentStatus status;
    private AuthUserDto user;

}
