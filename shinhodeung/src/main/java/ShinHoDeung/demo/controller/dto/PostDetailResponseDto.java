package ShinHoDeung.demo.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostDetailResponseDto {
    private Integer postId;
    private String title;
    private String content;
    private String category;
    private String authorName; // 익명일 경우 null
    private Boolean isAnonymous;
    private LocalDateTime createAt;
}
