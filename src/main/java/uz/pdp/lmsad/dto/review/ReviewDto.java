package uz.pdp.lmsad.dto.review;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

    private String id;
    private String text;
    private Integer rating;
    private String courseId;
    private String userId;
}
