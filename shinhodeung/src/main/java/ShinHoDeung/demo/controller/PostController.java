package ShinHoDeung.demo.controller;

import ShinHoDeung.demo.common.CommonResponse;
import ShinHoDeung.demo.common.StatusCode;
import ShinHoDeung.demo.controller.dto.PostDetailResponseDto;
import ShinHoDeung.demo.controller.dto.PostListPageDto;
import ShinHoDeung.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final StatusCode statusCode;

    @GetMapping("/search")
    public CommonResponse getPostList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "전체") String category
    ) {
        // 인증 정보 가져오기 (인증이 필요한 API)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal(); // 실제로는 사용하지 않지만 인증 확인 목적

        // 최신순 정렬
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createAt")));

        // 서비스 로직 처리
        PostListPageDto postListPageDto = postService.getPostList(category, pageable);

        return new CommonResponse(statusCode.SSU2000, postListPageDto, statusCode.SSU2000_MSG);
    }

    @GetMapping("/{postId}")
    public CommonResponse getPostDetail(@PathVariable Integer postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal(); // 인증 유효성 확보 목적

        PostDetailResponseDto dto = postService.getPostDetail(postId);
        return new CommonResponse(statusCode.SSU2000, dto, statusCode.SSU2000_MSG);
    }

}
