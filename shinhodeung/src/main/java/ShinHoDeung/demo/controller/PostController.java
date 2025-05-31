package ShinHoDeung.demo.controller;

import ShinHoDeung.demo.common.CommonResponse;
import ShinHoDeung.demo.common.StatusCode;
import ShinHoDeung.demo.controller.dto.*;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.service.CommentService;
import ShinHoDeung.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final StatusCode statusCode;
    private final CommentService commentService;

    @GetMapping("/search")
    public CommonResponse getPostList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "전체") String category
    ) {
        // 인증 정보 가져오기 (인증이 필요한 API)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();

        User loginUser = (User) authentication.getPrincipal();

        // 최신순 정렬
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createAt")));

        // 서비스 로직 처리
        PostListPageDto postListPageDto = postService.getPostList(category, pageable, loginUser);

        return new CommonResponse(statusCode.SSU2000, postListPageDto, statusCode.SSU2000_MSG);
    }

    @GetMapping("/{postId}")
    public CommonResponse getPostDetail(@PathVariable Integer postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal(); // 인증 유효성 확보 목적

        User user = (User) authentication.getPrincipal();

        PostDetailResponseDto dto = postService.getPostDetail(postId, user);
        return new CommonResponse(statusCode.SSU2000, dto, statusCode.SSU2000_MSG);
    }

    @PutMapping("/{postId}")
    public CommonResponse updatePost(@PathVariable Integer postId, @RequestBody PostUpdateRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        postService.updatePost(postId, requestDto, user);

        return new CommonResponse(statusCode.SSU2000, "게시글 수정 완료", statusCode.SSU2000_MSG);
    }

    @DeleteMapping("/{postId}")
    public CommonResponse deletePost(@PathVariable Integer postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        postService.deletePost(postId, user);

        return new CommonResponse(statusCode.SSU2000, "게시글 삭제 완료", statusCode.SSU2000_MSG);
    }

    @PostMapping
    public CommonResponse createPost(@RequestBody PostCreateRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        PostCreateResponseDto responseDto = postService.createPost(requestDto, user);

        return new CommonResponse(statusCode.SSU2000, responseDto, statusCode.SSU2000_MSG);
    }

    @PostMapping("/{postId}/like")
    public CommonResponse toggleLike(@PathVariable Integer postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        PostLikeResponseDto dto = postService.togglePostLike(postId, user);

        return new CommonResponse(statusCode.SSU2000, dto, statusCode.SSU2000_MSG);
    }

    @GetMapping("/{postId}/comment/search")
    public CommonResponse getComments(
            @PathVariable Integer postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createAt")));
        CommentListPageDto commentListPageDto = commentService.getCommentsByPostId(postId, pageable);
        return new CommonResponse(statusCode.SSU2000, commentListPageDto, statusCode.SSU2000_MSG);
    }




}
