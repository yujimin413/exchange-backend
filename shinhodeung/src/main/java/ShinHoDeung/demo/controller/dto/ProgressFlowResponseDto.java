package ShinHoDeung.demo.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProgressFlowResponseDto {
    
    @JsonProperty("user-main-step")
    private int mainStepOrder;
    
    @JsonProperty("user-sub-step")
    private int subStepOrder;
    
    @JsonProperty("main-steps")
    private List<MainStepDto> mainSteps;
}
