package uz.pdp.lmsad.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import uz.pdp.lmsad.dto.category.CategoryDto;
import uz.pdp.lmsad.dto.category.CreateCategoryDto;
import uz.pdp.lmsad.dto.category.UpdateCategoryDto;
import uz.pdp.lmsad.dto.course.CourseDto;
import uz.pdp.lmsad.dto.course.CreateCourseDto;
import uz.pdp.lmsad.dto.course.UpdateCourseDto;
import uz.pdp.lmsad.entity.Category;
import uz.pdp.lmsad.mapper.CategoryMapper;
import uz.pdp.lmsad.mapper.CourseMapper;
import uz.pdp.lmsad.repository.CategoryRepository;
import uz.pdp.lmsad.repository.CourseRepository;
import uz.pdp.lmsad.validator.CategoryValidator;
import uz.pdp.lmsad.validator.CourseValidator;

import java.util.List;

@Service
public class CategoryService
        extends
        AbstractService<
                CategoryRepository,
                CategoryMapper,
                CategoryValidator>
        implements
        CRUDService<
                CategoryDto,
                CreateCategoryDto,
                UpdateCategoryDto,
                String> {


    public CategoryService(CategoryRepository repository, CategoryMapper mapper, CategoryValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public CategoryDto create(CreateCategoryDto dto) {
        validator.validateOnCreate(dto);
        Category category = mapper.toEntity(dto);
        return mapper.toDto(repository.save(category));
    }

    @Override
    public CategoryDto update(String id, UpdateCategoryDto dto) {
        validator.validateOnUpdate(dto);
        Category category = repository.findById(id).orElseThrow();
        category.setName(dto.getName());
        return mapper.toDto(repository.save(category));
    }

    @Override
    public CategoryDto get(String id) {
        Category category = repository.findById(id).orElseThrow();
        return mapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categories = repository.findAll();
        return mapper.toDtoList(categories);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Category category = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Category not found")
        );
        repository.delete(id);
    }
}
