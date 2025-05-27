package ShinHoDeung.demo.exception;

import ShinHoDeung.demo.common.CommonResponse;
import ShinHoDeung.demo.common.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final StatusCode statusCode;

    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<CommonResponse> handleNoPermission(NoPermissionException e) {
        return ResponseEntity.status(403)
                .body(new CommonResponse(statusCode.SSU4003, null, e.getMessage()));
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<CommonResponse> handlePostNotFound(PostNotFoundException e) {
        return ResponseEntity.status(404)
                .body(new CommonResponse(statusCode.SSU4004, null, e.getMessage()));
    }

    // 다른 예외도 추가 가능
}
