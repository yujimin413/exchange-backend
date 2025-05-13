package ShinHoDeung.demo.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ShinHoDeung.demo.common.CommonResponse;
import ShinHoDeung.demo.common.StatusCode;
import ShinHoDeung.demo.controller.dto.UniversityLikeRequestDto;
import ShinHoDeung.demo.service.UniversityService;
import ShinHoDeung.demo.service.dto.UniversityAllReturnDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/university")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;
    private final StatusCode statusCode;

    @NotNull
    @GetMapping("/all")
    public CommonResponse getUniversityAll(){
        UniversityAllReturnDto universityAllReturnDto = universityService.getAllUniversity();
        
        return new CommonResponse(statusCode.SSU2040, universityAllReturnDto.toUniversityAllResponseDto(), statusCode.SSU2040_MSG);
    }

    @PostMapping("/like")
    public CommonResponse postUniversityLike(@RequestBody UniversityLikeRequestDto universityLikeRequestDto) {
        try{
            universityService.saveInterestedUniversity(universityLikeRequestDto.getUniversityId());
        }catch(Exception e){
            return new CommonResponse(statusCode.SSU4050, null, statusCode.SSU4050_MSG);
        }
        return new CommonResponse(statusCode.SSU2050, null, statusCode.SSU2050_MSG);
    }
    
}
