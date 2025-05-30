package ShinHoDeung.demo.controller;

import ShinHoDeung.demo.common.CommonResponse;
import ShinHoDeung.demo.common.StatusCode;
import ShinHoDeung.demo.controller.dto.CommentRequestDto;
import ShinHoDeung.demo.controller.dto.CommentResponseDto;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class CommentController {

    private final CommentService commentService;
    private final StatusCode statusCode;

    @PostMapping("/{postId}/comment")
    public CommonResponse addComment(@PathVariable Integer postId, @RequestBody CommentRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        CommentResponseDto responseDto = commentService.addComment(postId, requestDto, user);
        return new CommonResponse(statusCode.SSU2000, responseDto, "OK");
    }

    @DeleteMapping("/comment/{commentId}")
    public CommonResponse deleteComment(@PathVariable Integer commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        commentService.deleteComment(commentId, user);

        return new CommonResponse(statusCode.SSU2000, "댓글 삭제 완료", "OK");
    }

}