package ShinHoDeung.demo.service.dto;

import java.util.List;

import ShinHoDeung.demo.controller.dto.MainStepDto;
import ShinHoDeung.demo.controller.dto.ProgressFlowResponseDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProgressFlowRetrunDto {
    
    private int mainStepOrder;
    private int subStepOrder;
    private List<MainStepDto> mainStepDtos;   

    public ProgressFlowResponseDto toProgressFlowResponseDto(){
        return ProgressFlowResponseDto.builder()
                .mainStepOrder(mainStepOrder)
                .subStepOrder(subStepOrder)
                .mainSteps(mainStepDtos)
                .build();
    }
}