package uz.pdp.lmsad.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.pdp.lmsad.dto.assignment.AssignmentDto;
import uz.pdp.lmsad.dto.assignment.AssignmentResponseDto;
import uz.pdp.lmsad.entity.Assignment;
import uz.pdp.lmsad.entity.AssignmentResponse;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AssignmentResponseMapper {


    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "assignmentId",source = "assignment.id")
    public abstract AssignmentResponseDto toDto(AssignmentResponse save) ;
}