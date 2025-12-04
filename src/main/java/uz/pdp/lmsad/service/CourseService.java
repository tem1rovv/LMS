package uz.pdp.lmsad.service;

import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.pdp.lmsad.config.security.SessionUser;
import uz.pdp.lmsad.dto.admindashboard.CourseCountDto;
import uz.pdp.lmsad.dto.course.CourseDto;
import uz.pdp.lmsad.dto.course.CreateCourseDto;
import uz.pdp.lmsad.dto.course.UpdateCourseDto;
import uz.pdp.lmsad.entity.Category;
import uz.pdp.lmsad.entity.Course;
import uz.pdp.lmsad.entity.enums.CourseStatus;
import uz.pdp.lmsad.mapper.CourseMapper;
import uz.pdp.lmsad.repository.CategoryRepository;
import uz.pdp.lmsad.repository.CourseRepository;
import uz.pdp.lmsad.specification.CourseSpecification;
import uz.pdp.lmsad.validator.CourseValidator;

import java.util.List;

@Service
public class CourseService
        extends
        AbstractService<
                CourseRepository,
                CourseMapper,
                CourseValidator>
        implements
        CRUDService<
                CourseDto,
                CreateCourseDto,
                UpdateCourseDto,
                String> {


    private final CategoryRepository categoryRepository;
    private final SessionUser sessionUser;

    public CourseService(CourseRepository repository, CourseMapper mapper, CourseValidator validator, CategoryRepository categoryRepository, CategoryRepository categoryRepository1, SessionUser sessionUser) {
        super(repository, mapper, validator);
        this.categoryRepository = categoryRepository1;
        this.sessionUser = sessionUser;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames =  "courses",allEntries = true)
    public CourseDto create(CreateCourseDto dto) {
        validator.validateOnCreate(dto);
        String categoryId = dto.getCategoryId();
        Course course = mapper.toEntity(dto);
        course.setInstructor(sessionUser.user().getAuthUser());
        course.setStatus(CourseStatus.INACTIVE);
        course.setCategory(categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category Not Found")));
        Course saved = repository.save(course);
        return mapper.toDto(saved);
    }

    @Override
    @CachePut(cacheNames = "courses", key = "#id")
    public CourseDto update(String id, UpdateCourseDto dto) {
        validator.validateOnUpdate(dto);
        Course course = repository.findById(id).orElseThrow(() -> new RuntimeException("Course Not Found"));
        if (dto.getTitle() != null) {
            course.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            course.setDescription(dto.getDescription());
        }

        if (dto.getPrice() != null) {
            course.setPrice(dto.getPrice());
        }

        if (dto.getStatus() != null) {
            course.setStatus(dto.getStatus());
        }
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new RuntimeException("Category Not Found"));
            course.setCategory(category);
        }
        return mapper.toDto(repository.save(course));
    }

    @Override
    public CourseDto get(String id) {
        Course course = repository.findById(id).orElseThrow(() -> new RuntimeException("Course Not Found"));
        return mapper.toDto(course);

    }

    @Override
    public List<CourseDto> getAll() {
        List<Course> courses = repository.findAllByInstructorId(sessionUser.id());
        return mapper.toDtoList(courses);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "courses",key = "#id")
    public void delete(String id) {
        Course course = repository.findById(id).orElseThrow(() -> new RuntimeException("Course Not Found"));
        repository.delete(id);
    }

    public void activateCourse(String id) {
        Course course = repository.findById(id).orElseThrow(() -> new RuntimeException("Course Not Found"));
        course.setStatus(CourseStatus.ACTIVE);
        repository.save(course);
    }

    public void inActivateCourse(String id) {
        Course course = repository.findById(id).orElseThrow(() -> new RuntimeException("Course Not Found"));
        course.setStatus(CourseStatus.INACTIVE);
        repository.save(course);
    }

    public CourseCountDto getActiveCourseCount() {
        Integer count = repository.getCourseCount();
        CourseCountDto countDto = new CourseCountDto();
        countDto.setCount(count);
        return countDto;
    }

    public List<CourseDto> filter(String name, Double minPrice, Double maxPrice) {

        Specification<Course> spec =
                Specification.where(CourseSpecification.hasName(name))
                        .and(CourseSpecification.minPrice(minPrice))
                        .and(CourseSpecification.maxPrice(maxPrice));

        List<Course> courses = repository.findAll(spec);
       return mapper.toDtoList(courses);
    }

}
