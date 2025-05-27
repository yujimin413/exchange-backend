package ShinHoDeung.demo.controller.dto;

import lombok.Data;

@Data
public class PostUpdateRequestDto {
    private String title;
    private String content;
    private String category;
    private Boolean isAnonymous;
}
