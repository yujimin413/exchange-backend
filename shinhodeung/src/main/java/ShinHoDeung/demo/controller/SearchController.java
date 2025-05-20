package ShinHoDeung.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import ShinHoDeung.demo.common.CommonResponse;
import ShinHoDeung.demo.common.StatusCode;
import ShinHoDeung.demo.service.SearchService;
import ShinHoDeung.demo.service.dto.SearchReturnParamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
public class SearchController {
    
    private final SearchService searchService;
    private final StatusCode statusCode;

    @GetMapping("/search")
    public CommonResponse recommendSearchKeyword(@RequestParam String search) {
        System.out.println(search);
        SearchReturnParamDto searchReturnParamDto = searchService.getKeywords(search);
        return new CommonResponse(statusCode.SSU2000, searchReturnParamDto.toSearchResponseDto(),statusCode.SSU2000_MSG);
    }
    
}
