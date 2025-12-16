package uz.pdp.lmsad.service;

import io.reactivex.Completable;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.lmsad.dto.lesson.CreateLessonDto;
import uz.pdp.lmsad.dto.lesson.LessonDto;
import uz.pdp.lmsad.dto.lesson.UpdateLessonDto;
import uz.pdp.lmsad.dto.module.CreateModuleDto;
import uz.pdp.lmsad.dto.module.ModuleDto;
import uz.pdp.lmsad.dto.module.UpdateModuleDto;
import uz.pdp.lmsad.entity.Course;
import uz.pdp.lmsad.entity.Lesson;
import uz.pdp.lmsad.entity.Module;
import uz.pdp.lmsad.entity.enums.LessonType;
import uz.pdp.lmsad.mapper.LessonMapper;
import uz.pdp.lmsad.mapper.ModuleMapper;
import uz.pdp.lmsad.repository.CourseRepository;
import uz.pdp.lmsad.repository.LessonRepository;
import uz.pdp.lmsad.repository.ModuleRepository;
import uz.pdp.lmsad.strategy.SpeechToTextClient;
import uz.pdp.lmsad.util.TelegramService;
import uz.pdp.lmsad.validator.LessonValidator;
import uz.pdp.lmsad.validator.ModuleValidator;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class LessonService
        extends
        AbstractService<
                LessonRepository,
                LessonMapper,
                LessonValidator>
        implements
        CRUDService<
                LessonDto,
                CreateLessonDto,
                UpdateLessonDto,
                String> {


    private final ModuleRepository moduleRepository;
    private final TelegramService telegramService;
    private final SpeechToTextClient speechToTextClient;

    public LessonService(LessonRepository repository, LessonMapper mapper, LessonValidator validator, ModuleRepository moduleRepository, TelegramService telegramService, SpeechToTextClient speechToTextClient) {
        super(repository, mapper, validator);
        this.moduleRepository = moduleRepository;
        this.telegramService = telegramService;
        this.speechToTextClient = speechToTextClient;
    }

    @Override
    @SneakyThrows
    public LessonDto create(CreateLessonDto dto) {
        validator.validateOnCreate(dto);
        Module module = moduleRepository.findById(dto.getModuleId()).orElseThrow(() -> new RuntimeException("Module not found"));
        String contentUrl = telegramService.uploadFileToTelegram(dto.getContent());
        String transcribe = transcribe(dto.getContent()).get();
        Lesson lesson = Lesson
                .builder()
                .title(dto.getTitle())
                .lessonType(LessonType.VIDEO)
                .orderIndex(repository.countLessonByModuleId(module.getId()) + 1)
                .contentUrl(contentUrl)
                .transcribe(transcribe)
                .module(module)
                .build();
        return mapper.toDto(repository.save(lesson));
    }

    @Async
    public CompletableFuture<String> transcribe(MultipartFile file) {
        String transcribe = speechToTextClient.transcribe(file);
        return CompletableFuture.completedFuture(transcribe);
    }

    @Override
    @Transactional
    public LessonDto update(String id, UpdateLessonDto dto) {
        validator.validateOnUpdate(dto);
        Module module = moduleRepository.findById(dto.getPathModuleId()).orElseThrow();
        Lesson findLesson = module.getLessons().stream().filter(lesson -> lesson.getId().equals(id)).findFirst().orElseThrow();
        findLesson.setTitle(dto.getTitle());
        findLesson.setModule(moduleRepository.findById(dto.getModuleId()).orElseThrow());
        if (dto.getContent() != null) {
            findLesson.setContentUrl(telegramService.uploadFileToTelegram(dto.getContent()));
        }
        return mapper.toDto(repository.save(findLesson));
    }

    @Override
    public LessonDto get(String id) {
        return null;
    }

    @Override
    public List<LessonDto> getAll() {

        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Transactional(readOnly = true)
    public List<LessonDto> getAll(String id) {
        Module module = moduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Module not found"));
        List<Lesson> lessons = module.getLessons().stream().filter(lesson -> !lesson.isDelete()).toList();
        List<LessonDto> lessonDtos = mapper.toDtoList(lessons);
        lessonDtos.sort(Comparator.comparing(LessonDto::getOrderIndex));
        return lessonDtos;
    }

    @Transactional(readOnly = true)
    public LessonDto get(String moduleId, String lessonId) {
        Module module = moduleRepository.findById(moduleId).orElseThrow(() -> new RuntimeException("Module not found"));
        Lesson findLesson = module.getLessons().stream().filter(lesson -> lesson.getId().equals(lessonId) && !lesson.isDelete()).findFirst().orElseThrow(() -> new RuntimeException("Lesson not found"));
        return mapper.toDto(findLesson);
    }

    @Transactional
    public void delete(String moduleId, String lessonId) {
        Module module = moduleRepository.findById(moduleId).orElseThrow(() -> new RuntimeException("Module not found"));
        Lesson findLesson = module.getLessons().stream().filter(lesson -> lesson.getId().equals(lessonId)).findFirst().orElseThrow(() -> new RuntimeException("Lesson not found"));
        findLesson.setDelete(true);
        moduleRepository.save(module);
    }
}
