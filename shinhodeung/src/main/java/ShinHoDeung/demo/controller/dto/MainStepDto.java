package ShinHoDeung.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.*;

@Builder
@Data
public class MainStepDto {

    @JsonProperty("main_step_name")
    private String title;

    @JsonProperty("main_step_num")
    private int sortOrder;

    @JsonProperty("sub_steps")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SubStepDto> subStepDtos;
}
