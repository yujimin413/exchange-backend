package ShinHoDeung.demo.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProgressFlowResponseDto {
    
    @JsonProperty("user_main_step")
    private int mainStepOrder;
    
    @JsonProperty("user_sub_step")
    private int subStepOrder;
    
    @JsonProperty("main_steps")
    private List<MainStepDto> mainSteps;
}
