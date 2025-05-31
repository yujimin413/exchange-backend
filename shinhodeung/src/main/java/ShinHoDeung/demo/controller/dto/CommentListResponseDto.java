package ShinHoDeung.demo.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentListResponseDto {
    private Integer commentId;
    private String authorName;
    private Boolean isAnonymous;
    private String content;
    private LocalDateTime createdAt;
}
