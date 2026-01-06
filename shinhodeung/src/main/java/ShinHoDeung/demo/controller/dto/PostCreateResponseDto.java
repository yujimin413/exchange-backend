package ShinHoDeung.demo.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostCreateResponseDto {
    private Integer postId;
    private String title;
    private String category;
    private LocalDateTime createAt;
}
