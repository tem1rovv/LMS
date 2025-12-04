package uz.pdp.lmsad.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.pdp.lmsad.dto.CertificateDto;
import uz.pdp.lmsad.dto.module.CreateModuleDto;
import uz.pdp.lmsad.dto.module.ModuleDto;
import uz.pdp.lmsad.entity.Certificate;
import uz.pdp.lmsad.entity.Module;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CertificateMapper {


    public  abstract List<CertificateDto> toDtoList(List<Certificate> certificates);
}
