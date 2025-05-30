package ShinHoDeung.demo.service;

import ShinHoDeung.demo.controller.dto.CommentListPageDto;
import ShinHoDeung.demo.controller.dto.CommentListResponseDto;
import ShinHoDeung.demo.controller.dto.CommentRequestDto;
import ShinHoDeung.demo.controller.dto.CommentResponseDto;
import ShinHoDeung.demo.domain.Comment;
import ShinHoDeung.demo.domain.Post;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.exception.CommentNotFoundException;
import ShinHoDeung.demo.exception.NoPermissionException;
import ShinHoDeung.demo.exception.PostNotFoundException;
import ShinHoDeung.demo.repository.CommentRepository;
import ShinHoDeung.demo.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

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
                .authorName(comment.getIsAnonymous() ? null : user.getUserName())
                .isAnonymous(comment.getIsAnonymous())
                .content(comment.getContent())
                .createdAt(comment.getCreateAt())
                .build();
    }

    @Transactional
    public void deleteComment(Integer commentId, User loginUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getUser().getId().equals(loginUser.getId())) {
            throw new NoPermissionException("작성자만 댓글을 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }


    public CommentListPageDto getCommentsByPostId(Integer postId, Pageable pageable) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));

        Page<Comment> commentPage = commentRepository.findByPostOrderByCreateAtDesc(post, pageable);

        List<CommentListResponseDto> commentList = commentPage.getContent().stream().map(comment ->
                CommentListResponseDto.builder()
                        .commentId(comment.getId())
                        .authorName(comment.getIsAnonymous() ? null : comment.getUser().getUserName())
                        .isAnonymous(comment.getIsAnonymous())
                        .content(comment.getContent())
                        .createdAt(comment.getCreateAt())
                        .build()
        ).toList();

        return CommentListPageDto.builder()
                .content(commentList)
                .totalPages(commentPage.getTotalPages())
                .totalElements(commentPage.getTotalElements())
                .currentPage(commentPage.getNumber())
                .isLast(commentPage.isLast())
                .build();
    }



}