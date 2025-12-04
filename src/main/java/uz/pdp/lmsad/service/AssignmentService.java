package uz.pdp.lmsad.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.lmsad.dto.assignment.AssignmentDto;
import uz.pdp.lmsad.dto.assignment.CreateAssignmentDto;
import uz.pdp.lmsad.dto.assignment.CreateAssignmentSubmissionDto;
import uz.pdp.lmsad.dto.assignment.UpdateAssignmentDto;
import uz.pdp.lmsad.entity.Assignment;
import uz.pdp.lmsad.entity.AssignmentSubmission;
import uz.pdp.lmsad.entity.Lesson;
import uz.pdp.lmsad.mapper.AssignmentMapper;
import uz.pdp.lmsad.repository.AssignmentRepository;
import uz.pdp.lmsad.repository.AssignmentSubmissionRepository;
import uz.pdp.lmsad.repository.AuthUserRepository;
import uz.pdp.lmsad.repository.LessonRepository;
import uz.pdp.lmsad.validator.AssignmentValidator;

import java.util.List;

@Service
public class AssignmentService
        extends
        AbstractService<
                AssignmentRepository,
                AssignmentMapper,
                AssignmentValidator>
        implements
        CRUDService<
                AssignmentDto,
                CreateAssignmentDto,
                UpdateAssignmentDto,
                String> {


    private final LessonRepository lessonRepository;
    private final AuthUserRepository authUserRepository;
    private final AssignmentSubmissionRepository assignmentSubmissionRepository;

    public AssignmentService(AssignmentRepository repository, AssignmentMapper mapper, AssignmentValidator validator, LessonRepository lessonRepository, LessonRepository lessonRepository1, AuthUserRepository authUserRepository, AssignmentSubmissionRepository assignmentSubmissionRepository) {
        super(repository, mapper, validator);
        this.lessonRepository = lessonRepository1;
        this.authUserRepository = authUserRepository;
        this.assignmentSubmissionRepository = assignmentSubmissionRepository;
    }

    @Override
    public AssignmentDto create(CreateAssignmentDto dto) {

        validator.validateOnCreate(dto);

        Lesson lesson = lessonRepository.findById(dto.getLessonId()).orElseThrow(() -> new RuntimeException("lesson not found"));
        Assignment assignment = Assignment
                .builder()
                .text(dto.getText())
                .lesson(lesson)
                .build();
        return mapper.toDto(repository.save(assignment));
    }

    @Override
    public AssignmentDto update(String id, UpdateAssignmentDto dto) {
        return null;
    }

    @Override
    public AssignmentDto get(String id) {
        return null;
    }

    @Override
    public List<AssignmentDto> getAll() {
        return List.of();
    }

    @Override
    public void delete(String id) {

    }

    @Transactional(readOnly = true)
    public List<AssignmentDto> getAll(String id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow();
        List<Assignment> assignments = lesson.getAssignments();
        return mapper.toDtoList(assignments);
    }

    @Transactional(readOnly = true)
    public AssignmentDto get(String lessonId, String assignmentId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        Assignment findAssignment = lesson
                .getAssignments()
                .stream()
                .filter(assignment -> assignment.getId().equals(assignmentId)).findFirst().orElseThrow(() -> new RuntimeException("assignment not found"));
        return mapper.toDto(findAssignment);
    }

    @Async
    public void createAssignmentSubmission(String id,CreateAssignmentSubmissionDto dto) {

        Assignment assignment = repository.findById(id).orElseThrow();
        AssignmentSubmission assignmentSubmission = AssignmentSubmission
                .builder()
                .assignment(assignment)
                .user(authUserRepository.findById(dto.getUserId()).orElseThrow())
                .grade(dto.getGrade())
                .build();
        assignmentSubmissionRepository.save(assignmentSubmission);
    }
}
