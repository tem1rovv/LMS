package uz.pdp.lmsad.dto.lesson;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateLessonDto {
    private String title;

    @JsonIgnore
    private MultipartFile content;
    @JsonIgnore
    private String moduleId;
}
