package uz.pdp.lmsad.dto.instructordashboard;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class CourseUserCount {
    private Integer count;
}
