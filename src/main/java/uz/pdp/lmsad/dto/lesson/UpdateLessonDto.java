package uz.pdp.lmsad.dto.lesson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateLessonDto {

    private String title;
    @JsonIgnore
    private MultipartFile content;
    @JsonIgnore
    private String pathModuleId;
    private String moduleId;
}
