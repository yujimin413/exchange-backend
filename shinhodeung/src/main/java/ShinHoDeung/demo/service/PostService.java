package ShinHoDeung.demo.service;

import ShinHoDeung.demo.controller.dto.*;
import ShinHoDeung.demo.domain.Post;
import ShinHoDeung.demo.domain.PostLike;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.exception.NoPermissionException;
import ShinHoDeung.demo.exception.PostNotFoundException;
import ShinHoDeung.demo.repository.CommentRepository;
import ShinHoDeung.demo.repository.PostLikeRepository;
import ShinHoDeung.demo.repository.PostRepository;
import ShinHoDeung.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    @NotNull
    public PostListPageDto getPostList(@NotNull String category, @NotNull Pageable pageable, @NotNull User loginUser) {
        Page<Post> postPage;

        if (category.equals("전체")) {
            postPage = postRepository.findAll(pageable);
        } else {
            postPage = postRepository.findByCategory(category, pageable);
        }

        // Post → PostListResponseDto 변환
        List<PostListResponseDto> postDtoList = postPage.getContent().stream().map(post -> {
            int likeCount = postLikeRepository.countByPost(post);
            int commentCount = commentRepository.countByPost(post);
            boolean likedByMe = postLikeRepository.existsByPostAndUser(post, loginUser);

            // 미리보기 내용: 50자까지만
            String preview = post.getContent();
            if (preview.length() > 60) {
                preview = preview.substring(0, 60);
            }

            return PostListResponseDto.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .previewContent(preview)
                    .category(post.getCategory())
                    .authorName(Boolean.TRUE.equals(post.getIsAnonymous()) ? null : post.getUser().getUserName())
                    .createAt(post.getCreateAt())
                    .likeCount(likeCount)
                    .commentCount(commentCount)
                    .likedByMe(likedByMe)
                    .build();
        }).toList();

        return PostListPageDto.builder()
                .content(postDtoList)
                .totalPages(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .currentPage(postPage.getNumber())
                .isLast(postPage.isLast())
                .build();
    }


    public PostDetailResponseDto getPostDetail(@NotNull Integer postId, @NotNull User loginUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));

        int likeCount = postLikeRepository.countByPost(post);
        int commentCount = commentRepository.countByPost(post);
        boolean likedByMe = postLikeRepository.existsByPostAndUser(post, loginUser);

        return PostDetailResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .authorName(post.getIsAnonymous() ? null : post.getUser().getUserName())
                .isAnonymous(post.getIsAnonymous())
                .createAt(post.getCreateAt())
                .likeCount(likeCount)
                .commentCount(commentCount)
                .likedByMe(likedByMe)
                .build();
    }

    @Transactional
    public void updatePost(Integer postId, PostUpdateRequestDto dto, User loginUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        // 작성자 본인인지 확인
        if (!post.getUser().getId().equals(loginUser.getId())) {
            throw new NoPermissionException("작성자만 게시글을 수정할 수 있습니다.");
        }

        // 내용 수정
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setCategory(dto.getCategory());
        post.setIsAnonymous(dto.getIsAnonymous());

        postRepository.save(post); // 생략 가능 (영속 상태 유지 중이면 자동 플러시됨)
    }

    @Transactional
    public void deletePost(Integer postId, User loginUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        // 작성자 본인인지 확인
        if (!post.getUser().getId().equals(loginUser.getId())) {
            throw new NoPermissionException("작성자만 게시글을 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    @Transactional
    public PostCreateResponseDto createPost(PostCreateRequestDto dto, User user) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setCategory(dto.getCategory());
        post.setIsAnonymous(Boolean.TRUE.equals(dto.getIsAnonymous()));
        post.setUser(user);
        post.setCreateAt(LocalDateTime.now());

        postRepository.save(post);

        return PostCreateResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .category(post.getCategory())
                .createAt(post.getCreateAt())
                .build();
    }


    @Transactional
    public PostLikeResponseDto togglePostLike(Integer postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        Optional<PostLike> existingLike = postLikeRepository.findByPostAndUser(post, user);

        if (existingLike.isPresent()) {
            // 이미 좋아요 누름 → 삭제 (취소)
            postLikeRepository.delete(existingLike.get());
            return PostLikeResponseDto.builder().liked(false).build();
        } else {
            // 좋아요 안 누름 → 등록
            PostLike newLike = new PostLike();
            newLike.setPost(post);
            newLike.setUser(user);
            newLike.setCreatedAt(LocalDateTime.now());
            postLikeRepository.save(newLike);
            return PostLikeResponseDto.builder().liked(true).build();
        }
    }





}
