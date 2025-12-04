package uz.pdp.lmsad.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.lmsad.dto.module.CreateModuleDto;
import uz.pdp.lmsad.dto.module.ModuleDto;
import uz.pdp.lmsad.dto.module.UpdateModuleDto;
import uz.pdp.lmsad.entity.Course;
import uz.pdp.lmsad.entity.Module;
import uz.pdp.lmsad.mapper.ModuleMapper;
import uz.pdp.lmsad.repository.CourseRepository;
import uz.pdp.lmsad.repository.ModuleRepository;
import uz.pdp.lmsad.validator.ModuleValidator;

import java.util.Comparator;
import java.util.List;

@Service
public class ModuleService
        extends
        AbstractService<
                ModuleRepository,
                ModuleMapper,
                ModuleValidator>
        implements
        CRUDService<
                ModuleDto,
                CreateModuleDto,
                UpdateModuleDto,
                String> {


    private final CourseRepository courseRepository;

    public ModuleService(ModuleRepository repository, ModuleMapper mapper, ModuleValidator validator, CourseRepository courseRepository) {
        super(repository, mapper, validator);
        this.courseRepository = courseRepository;
    }

    @Override
    public ModuleDto create(CreateModuleDto dto) {
        validator.validateOnCreate(dto);
        Course course = courseRepository.findById(dto.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found"));
        int moduleCount = repository.countModuleByCourseId(course.getId());
        Module module = Module
                .builder()
                .name(dto.getName())
                .course(course)
                .orderIndex(moduleCount+1)
                .build();
        return mapper.toDto(repository.save(module));
    }

    @Override
    public ModuleDto update(String id, UpdateModuleDto dto) {
        return null;
    }

    @Override
    public ModuleDto get(String id) {
        Module module = repository.findById(id).orElseThrow(() -> new RuntimeException("Module not found"));
        return mapper.toDto(module);
    }

    @Override
    public List<ModuleDto> getAll() {
        return List.of();
    }

    @Override
    @Transactional(rollbackFor =  Exception.class)
    public void delete(String id) {
        Module module = repository.findById(id).orElseThrow(() -> new RuntimeException("Module not found"));
        repository.delete(module.getId());
    }

    @Transactional(readOnly = true)
    public List<ModuleDto> getAll(String id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        List<Module> modules = course.getModules();
        modules.sort(Comparator.comparing(Module::getOrderIndex));
        return mapper.toDtoList(modules);
    }
}
