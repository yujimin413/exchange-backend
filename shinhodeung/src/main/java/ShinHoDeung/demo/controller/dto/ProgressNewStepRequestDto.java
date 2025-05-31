package ShinHoDeung.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import ShinHoDeung.demo.service.dto.ProgressNewStatusParamDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProgressNewStepRequestDto {
    @JsonProperty("main_step_num")
    private Integer mainStepOrder;
    @JsonProperty("sub_step_num")
    private Integer subStepOrder;

    public ProgressNewStatusParamDto toProgressNewStatusParamDto(){
        return ProgressNewStatusParamDto.builder()
            .mainStepOrder(mainStepOrder)
            .subStepOrder(subStepOrder)
            .build();
    }
}
