package ShinHoDeung.demo.service.dto;

import java.util.List;

import ShinHoDeung.demo.controller.dto.Keyword;
import ShinHoDeung.demo.controller.dto.SearchResponseDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchReturnParamDto {
    private List<Keyword> keywords;

    public SearchResponseDto toSearchResponseDto(){
        return SearchResponseDto.builder().keywords(keywords).build();
    }
}
