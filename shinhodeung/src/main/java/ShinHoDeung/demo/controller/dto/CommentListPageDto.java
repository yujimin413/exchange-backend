package ShinHoDeung.demo.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommentListPageDto {
    private List<CommentListResponseDto> content;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private boolean isLast;
}
