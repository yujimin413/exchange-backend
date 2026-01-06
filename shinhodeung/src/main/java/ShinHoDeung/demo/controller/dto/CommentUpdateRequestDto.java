package ShinHoDeung.demo.controller.dto;

import lombok.Data;

@Data
public class CommentUpdateRequestDto {
    private String content;
    private Boolean isAnonymous;
}
