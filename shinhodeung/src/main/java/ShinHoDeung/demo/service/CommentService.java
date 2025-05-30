package ShinHoDeung.demo.service;

import ShinHoDeung.demo.controller.dto.CommentRequestDto;
import ShinHoDeung.demo.controller.dto.CommentResponseDto;
import ShinHoDeung.demo.domain.Comment;
import ShinHoDeung.demo.domain.Post;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.exception.PostNotFoundException;
import ShinHoDeung.demo.repository.CommentRepository;
import ShinHoDeung.demo.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto addComment(Integer postId, CommentRequestDto dto, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(dto.getContent())
                .isAnonymous(Boolean.TRUE.equals(dto.getIsAnonymous()))
                .createAt(LocalDateTime.now())
                .build();


        commentRepository.save(comment);

        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .authorName(user.getUserName())
                .isAnonymous(comment.getIsAnonymous())
                .content(comment.getContent())
                .createdAt(comment.getCreateAt())
                .build();
    }
}