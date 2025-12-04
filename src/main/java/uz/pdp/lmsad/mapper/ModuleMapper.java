package uz.pdp.lmsad.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.pdp.lmsad.dto.course.CourseDto;
import uz.pdp.lmsad.dto.course.CreateCourseDto;
import uz.pdp.lmsad.dto.module.CreateModuleDto;
import uz.pdp.lmsad.dto.module.ModuleDto;
import uz.pdp.lmsad.entity.Course;
import uz.pdp.lmsad.entity.Module;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ModuleMapper {


    public abstract Module toEntity(CreateModuleDto dto) ;

    @Mapping(target = "courseId",source = "course.id")
    public abstract ModuleDto toDto(Module save);

    public abstract List<ModuleDto> toDtoList(List<Module> modules);
}
