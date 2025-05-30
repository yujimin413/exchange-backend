package ShinHoDeung.demo.service.dto;

import ShinHoDeung.demo.domain.Post;
import ShinHoDeung.demo.domain.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentCreateDto {
    private Post post;
    private User user;
    private String content;
    private LocalDateTime createdAt;
}