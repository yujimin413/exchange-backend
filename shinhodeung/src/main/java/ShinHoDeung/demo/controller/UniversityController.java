package ShinHoDeung.demo.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ShinHoDeung.demo.common.CommonResponse;
import ShinHoDeung.demo.common.StatusCode;
import ShinHoDeung.demo.controller.dto.UniversityFilterRequestDto;
import ShinHoDeung.demo.controller.dto.UniversityLikeRequestDto;
import ShinHoDeung.demo.exception.NoSuchUniversityException;
import ShinHoDeung.demo.service.UniversityService;
import ShinHoDeung.demo.service.dto.UniversityAllReturnDto;
import ShinHoDeung.demo.service.dto.UniversityDetailReturnDto;
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
            if(universityLikeRequestDto.getLike())
                universityService.saveInterestedUniversity(universityLikeRequestDto.getUniversityId());
            else
                universityService.deleteInterestedUniversity(universityLikeRequestDto.getUniversityId());;
        }catch(NoSuchUniversityException e){
            return new CommonResponse(statusCode.SSU4050, null, statusCode.SSU4050_MSG);
        }
        return new CommonResponse(statusCode.SSU2050, null, statusCode.SSU2050_MSG);
    }
    
    @GetMapping("/detail")
    public CommonResponse getUniversityDetail(@RequestParam String universityId){
        UniversityDetailReturnDto universityDetailReturnDto;

        try{
            universityDetailReturnDto = universityService.getUnviersityDetail(universityId);
        }catch(NoSuchUniversityException e){
            return new CommonResponse(statusCode.SSU4060, null, statusCode.SSU4060_MSG);    
        }
        
        return new CommonResponse(statusCode.SSU2060, universityDetailReturnDto.toUniversityDetailResponseDto(), statusCode.SSU2060_MSG);
    }

    @PostMapping("/filtered")
    public CommonResponse postUniversityFiltered(@RequestBody UniversityFilterRequestDto universityFilterRequestDto){
        
        return new CommonResponse(null, null, null);
    }
}
