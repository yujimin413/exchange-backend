package ShinHoDeung.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ShinHoDeung.demo.common.CommonResponse;
import ShinHoDeung.demo.common.StatusCode;
import ShinHoDeung.demo.controller.dto.CheckStatusRequestDto;
import ShinHoDeung.demo.controller.dto.ProgressAddCheckboxResponseDto;
import ShinHoDeung.demo.controller.dto.ProgressGetUnivChoiceResponseDto;
import ShinHoDeung.demo.controller.dto.ProgressUpdateComponentRequestDto;
import ShinHoDeung.demo.controller.dto.ProgressNewStepRequestDto;
import ShinHoDeung.demo.controller.dto.ProgressPostUnivChoiceRequestDto;
import ShinHoDeung.demo.service.ProgressService;
import ShinHoDeung.demo.service.dto.ProgressFlowRetrunDto;
import ShinHoDeung.demo.service.dto.ProgressGetUnivChoiceReturnDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




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

    @PostMapping("/newstep")
    public CommonResponse updateNewStep(@RequestBody ProgressNewStepRequestDto progressNewStepRequestDto) {
        progressService.updateNewStep(progressNewStepRequestDto.toProgressNewStatusParamDto());
        
        return new CommonResponse(statusCode.SSU2000, null, statusCode.SSU2000_MSG);
    }
    
    
    @PostMapping("/component/{componentId}/check")
    public CommonResponse changeCheckStatus(@PathVariable Integer componentId, @RequestBody CheckStatusRequestDto checkStatusRequestDto) {
        try{
            progressService.updateCheckStatus(checkStatusRequestDto.toProgressCheckStatusParamDto(componentId));
        } catch(EntityNotFoundException e){
            return new CommonResponse(statusCode.SSU4000,"존재하지 않는 component Id입니다.",statusCode.SSU4000_MSG);
        }
        
        return new CommonResponse(statusCode.SSU2000,null,statusCode.SSU2000_MSG);
    }

    @PutMapping("/component/{componentId}")
    public CommonResponse updateComponent(@PathVariable Integer componentId, @RequestBody ProgressUpdateComponentRequestDto progressNewCheckboxRequestDto) {
        try{
            progressService.updateComponent(progressNewCheckboxRequestDto.toProgressDatePlusParamDto(componentId));
        } catch(EntityNotFoundException e){
            return new CommonResponse(statusCode.SSU4000,"존재하지 않는 component Id입니다.",statusCode.SSU4000_MSG);
        } catch(IllegalArgumentException e){
            return new CommonResponse(statusCode.SSU4000,e.getMessage(), statusCode.SSU4000_MSG);
        }
        
        return new CommonResponse(statusCode.SSU2000, null, statusCode.SSU2000_MSG);
    }

    @DeleteMapping("/component/{componentId}")
    public CommonResponse deleteComponent(@PathVariable Integer componentId) {
        try{
            progressService.deleteComponent(componentId);
        } catch(EntityNotFoundException e){
            return new CommonResponse(statusCode.SSU4000, "잘못된 componentId입니다.", statusCode.SSU4000_MSG);
        } catch(IllegalArgumentException e){
            return new CommonResponse(statusCode.SSU4000, "CHECK_BOX만 삭제 가능합니다.", statusCode.SSU4000_MSG);
        }
        return new CommonResponse(statusCode.SSU2000, null, statusCode.SSU2000_MSG);
    }

    @PostMapping("/detail/{detailId}/checkbox")
    public CommonResponse addCheckBox(@PathVariable Integer detailId){
        
        Integer componentId = progressService.addCheckBox(detailId);
        ProgressAddCheckboxResponseDto progressAddCheckboxResponseDto = new ProgressAddCheckboxResponseDto(componentId);

        return new CommonResponse(statusCode.SSU2000, progressAddCheckboxResponseDto, statusCode.SSU2000_MSG);
    }

    @GetMapping("/univ/choice")
    public CommonResponse getUnivSelection() {
        ProgressGetUnivChoiceReturnDto progressGetUnivChoiceReturnDto = progressService.getUnivSelection();
        return new CommonResponse(statusCode.SSU2000, progressGetUnivChoiceReturnDto.toProgressGetUnivChoiceResponseDto(), statusCode.SSU2000_MSG);
    }
 
    @PostMapping("/univ/choice")
    public CommonResponse postUnivSelection(@RequestBody ProgressPostUnivChoiceRequestDto progressPostUnivChoiceRequestDto) {
        try{
            progressService.postUnivSelection(progressPostUnivChoiceRequestDto.tProgressPostUnivChoiceParamDto());
        } catch (EntityNotFoundException e){
            return new CommonResponse(statusCode.SSU4000, "잘못된 univId입니다.", statusCode.SSU4000_MSG);
        }
        return new CommonResponse(statusCode.SSU2000, null, statusCode.SSU2000_MSG);
    }
}
