package uz.pdp.lmsad.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.pdp.lmsad.dto.assignment.AssignmentDto;
import uz.pdp.lmsad.dto.lesson.LessonDto;
import uz.pdp.lmsad.entity.Assignment;
import uz.pdp.lmsad.entity.Lesson;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AssignmentMapper {


    @Mapping(target = "lessonId",source = "lesson.id")
    public abstract AssignmentDto toDto(Assignment save);

    public abstract List<AssignmentDto> toDtoList(List<Assignment> assignments);
}