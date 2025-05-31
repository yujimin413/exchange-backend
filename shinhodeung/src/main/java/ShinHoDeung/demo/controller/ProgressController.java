package ShinHoDeung.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ShinHoDeung.demo.common.CommonResponse;
import ShinHoDeung.demo.common.StatusCode;
import ShinHoDeung.demo.service.ProgressService;
import ShinHoDeung.demo.service.dto.ProgressFlowRetrunDto;
import lombok.RequiredArgsConstructor;


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
}
