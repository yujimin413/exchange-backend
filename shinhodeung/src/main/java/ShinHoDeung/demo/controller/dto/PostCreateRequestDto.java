package ShinHoDeung.demo.controller.dto;

import lombok.Data;

@Data
public class PostCreateRequestDto {
    private String title;
    private String content;
    private String category;
    private Boolean isAnonymous;
}
