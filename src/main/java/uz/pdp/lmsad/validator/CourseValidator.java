package uz.pdp.lmsad.validator;


import org.springframework.stereotype.Component;
import uz.pdp.lmsad.dto.auth.CreateAuthUserDto;
import uz.pdp.lmsad.dto.course.CreateCourseDto;
import uz.pdp.lmsad.dto.course.UpdateCourseDto;
import uz.pdp.lmsad.repository.AuthUserRepository;

@Component
public class CourseValidator {

    public void validateOnCreate(CreateCourseDto dto) {

    }

    public void validateOnUpdate(UpdateCourseDto dto) {

    }
}
