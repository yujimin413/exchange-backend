package ShinHoDeung.demo.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDto {
    private Integer commentId;
    private String authorName;
    private String content;
    private LocalDateTime createdAt;
    private Boolean isAnonymous;
}