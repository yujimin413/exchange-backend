package ShinHoDeung.demo.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostListResponseDto {
    private Integer postId;
    private String title;
    private String category;
    private String authorName; // 익명 여부에 따라 null 또는 이름
    private LocalDateTime createAt;

    private Integer likeCount;
    private Integer commentCount;
    private Boolean likedByMe;

    private String previewContent;
    private Boolean isMine;
}
