package uz.pdp.lmsad.dto.module;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateModuleDto {

    private String name;
    @JsonIgnore
    private  String courseId;
}
