package ShinHoDeung.demo.service;

import ShinHoDeung.demo.controller.dto.*;
import ShinHoDeung.demo.domain.Post;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.exception.NoPermissionException;
import ShinHoDeung.demo.exception.PostNotFoundException;
import ShinHoDeung.demo.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @NotNull
    public PostListPageDto getPostList(@NotNull String category, @NotNull Pageable pageable) {
        Page<Post> postPage;

        // "전체"일 경우 모든 게시글 조회, 아니면 카테고리 필터링
        if (category.equals("전체")) {
            postPage = postRepository.findAll(pageable);
        } else {
            postPage = postRepository.findByCategory(category, pageable);
        }

        // Post → PostListResponseDto 변환
        List<PostListResponseDto> postDtoList = postPage.getContent().stream().map(post ->
                PostListResponseDto.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .category(post.getCategory())
                        .authorName(post.getIsAnonymous() ? null : post.getUser().getUserName())
                        .createAt(post.getCreateAt())
                        .build()
        ).toList();

        // 페이징 결과 포함한 DTO로 반환
        return PostListPageDto.builder()
                .content(postDtoList)
                .totalPages(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .currentPage(postPage.getNumber())
                .isLast(postPage.isLast())
                .build();
    }

    public PostDetailResponseDto getPostDetail(@NotNull Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));

        return PostDetailResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .authorName(post.getIsAnonymous() ? null : post.getUser().getUserName())
                .isAnonymous(post.getIsAnonymous())
                .createAt(post.getCreateAt())
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







}
