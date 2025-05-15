package ShinHoDeung.demo.controller.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchResponseDto {
    private List<Keyword> keywords;
}
