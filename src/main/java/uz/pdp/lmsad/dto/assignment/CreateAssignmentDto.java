package uz.pdp.lmsad.dto.assignment;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateAssignmentDto {
    private String text;
    @JsonIgnore
    private String lessonId;
}
