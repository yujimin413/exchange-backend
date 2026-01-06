package ShinHoDeung.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import ShinHoDeung.demo.common.CommonResponse;
import ShinHoDeung.demo.common.StatusCode;
import ShinHoDeung.demo.service.HomeService;
import ShinHoDeung.demo.service.dto.HomeReturnDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    
    private final HomeService homeService;
    private final StatusCode statusCode;

    @GetMapping
    public CommonResponse getHome() {
        HomeReturnDto homeReturnDto = homeService.getHome();
        return new CommonResponse(statusCode.SSU2000, homeReturnDto.toHomeResponseDto(), statusCode.SSU2000_MSG);
    }
    
}
