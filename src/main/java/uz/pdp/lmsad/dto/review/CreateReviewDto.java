package uz.pdp.lmsad.dto.review;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReviewDto {

    private String text;
    private Integer rating;
}

