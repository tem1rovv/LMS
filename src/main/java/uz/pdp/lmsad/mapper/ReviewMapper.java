package uz.pdp.lmsad.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.pdp.lmsad.dto.module.CreateModuleDto;
import uz.pdp.lmsad.dto.module.ModuleDto;
import uz.pdp.lmsad.dto.review.ReviewDto;
import uz.pdp.lmsad.entity.Module;
import uz.pdp.lmsad.entity.Review;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ReviewMapper {


    @Mapping(target = "courseId",source = "course.id")
    @Mapping(target = "userId",source = "user.id")
    public abstract ReviewDto toDto(Review saved);

    public abstract List<ReviewDto> toDtoList(List<Review> reviews);
}
