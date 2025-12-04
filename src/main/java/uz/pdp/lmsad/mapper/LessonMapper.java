package uz.pdp.lmsad.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.pdp.lmsad.dto.lesson.LessonDto;
import uz.pdp.lmsad.dto.module.CreateModuleDto;
import uz.pdp.lmsad.dto.module.ModuleDto;
import uz.pdp.lmsad.entity.Lesson;
import uz.pdp.lmsad.entity.Module;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class LessonMapper {


    @Mapping(target = "moduleId",source = "module.id")
    public abstract LessonDto toDto(Lesson save) ;

    public abstract List<LessonDto> toDtoList(List<Lesson> lessons);

}
