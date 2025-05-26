package ShinHoDeung.demo.controller.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PostListPageDto {
    private List<PostListResponseDto> content;
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private Boolean isLast;
}
