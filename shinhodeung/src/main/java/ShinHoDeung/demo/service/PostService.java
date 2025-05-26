package ShinHoDeung.demo.service;

import ShinHoDeung.demo.controller.dto.PostDetailResponseDto;
import ShinHoDeung.demo.controller.dto.PostListPageDto;
import ShinHoDeung.demo.controller.dto.PostListResponseDto;
import ShinHoDeung.demo.domain.Post;
import ShinHoDeung.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

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

}
