package ShinHoDeung.demo.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostLikeResponseDto {
    private boolean liked;
}
