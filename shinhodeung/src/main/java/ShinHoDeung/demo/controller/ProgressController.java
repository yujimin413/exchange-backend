package ShinHoDeung.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ShinHoDeung.demo.common.CommonResponse;
import ShinHoDeung.demo.common.StatusCode;
import ShinHoDeung.demo.controller.dto.CheckStatusRequestDto;
import ShinHoDeung.demo.service.ProgressService;
import ShinHoDeung.demo.service.dto.ProgressFlowRetrunDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/progress")
@RequiredArgsConstructor
public class ProgressController {
    
    private final ProgressService progressService;
    private final StatusCode statusCode;

    @GetMapping("/flow")
    public CommonResponse getProgressFlow() {
        ProgressFlowRetrunDto progressFlowRetrunDto;
        
        progressFlowRetrunDto = progressService.getProgressFlow();
        
        return new CommonResponse(statusCode.SSU2000, progressFlowRetrunDto.toProgressFlowResponseDto(), statusCode.SSU2000_MSG);
    }

    @PostMapping("/{componentId}/check")
    public CommonResponse changeCheckStatus(@PathVariable Integer componentId, @RequestBody CheckStatusRequestDto checkStatusRequestDto) {
        try{
            progressService.changeCheckStatus(checkStatusRequestDto.toCheckStatusParamDto(componentId));
        } catch(EntityNotFoundException e){
            return new CommonResponse(statusCode.SSU4000,"존재하지 않는 component Id입니다.",statusCode.SSU4000_MSG);
        }
        
        return new CommonResponse(statusCode.SSU2000,null,statusCode.SSU2000_MSG);
    }
    
}
