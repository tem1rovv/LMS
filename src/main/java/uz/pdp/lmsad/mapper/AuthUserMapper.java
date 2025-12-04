package uz.pdp.lmsad.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import uz.pdp.lmsad.dto.auth.AuthUserDto;
import uz.pdp.lmsad.dto.auth.CreateAuthUserDto;
import uz.pdp.lmsad.entity.AuthUser;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AuthUserMapper {


    public abstract AuthUserDto toDto(AuthUser authUser);

    public abstract AuthUser toEntity(CreateAuthUserDto dto);

    public abstract List<AuthUserDto> toListDto(List<AuthUser> users);
}
