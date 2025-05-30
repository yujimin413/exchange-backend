package ShinHoDeung.demo.controller.dto;

import lombok.Data;

@Data
public class CommentRequestDto {
    private String content;
    private Boolean isAnonymous;
}