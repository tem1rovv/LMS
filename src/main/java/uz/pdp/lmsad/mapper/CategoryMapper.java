package uz.pdp.lmsad.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.pdp.lmsad.dto.category.CategoryDto;
import uz.pdp.lmsad.dto.category.CreateCategoryDto;
import uz.pdp.lmsad.entity.Category;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CategoryMapper {


    public abstract Category toEntity(CreateCategoryDto dto);

    public abstract CategoryDto toDto(Category category);

    public abstract List<CategoryDto> toDtoList(List<Category> categories);
}
