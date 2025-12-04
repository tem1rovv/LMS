package uz.pdp.lmsad.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.pdp.lmsad.dto.enroll.EnrollmentDto;
import uz.pdp.lmsad.dto.module.CreateModuleDto;
import uz.pdp.lmsad.dto.module.ModuleDto;
import uz.pdp.lmsad.entity.Enrollment;
import uz.pdp.lmsad.entity.Module;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EnrollmentMapper {

    public abstract EnrollmentDto toDto(Enrollment save) ;



    public abstract List<EnrollmentDto> toDtoList(List<Enrollment> enrollments) ;
}
