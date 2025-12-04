package uz.pdp.lmsad.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.pdp.lmsad.dto.auth.AuthUserDto;
import uz.pdp.lmsad.dto.auth.CreateAuthUserDto;
import uz.pdp.lmsad.dto.course.CourseDto;
import uz.pdp.lmsad.dto.course.CreateCourseDto;
import uz.pdp.lmsad.entity.AuthUser;
import uz.pdp.lmsad.entity.Course;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CourseMapper {


//    @Mapping(target = "category",source = "categoryId")
    public abstract Course toEntity(CreateCourseDto dto) ;

    public abstract CourseDto toDto(Course course);

    public abstract List<CourseDto> toDtoList(List<Course> courses);
}
