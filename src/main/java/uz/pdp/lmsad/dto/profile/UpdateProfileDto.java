package uz.pdp.lmsad.dto.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateProfileDto {

    private String fullName;
    private String phoneNumber;
    private MultipartFile profileImage;
}
