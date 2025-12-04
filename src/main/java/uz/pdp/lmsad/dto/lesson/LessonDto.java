package uz.pdp.lmsad.dto.lesson;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonDto {

    private String id;
    private String title;
    private String contentUrl;
    private Integer orderIndex;
    private String moduleId;
}
