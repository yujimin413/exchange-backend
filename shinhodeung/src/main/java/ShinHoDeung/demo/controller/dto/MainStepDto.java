package ShinHoDeung.demo.controller.dto;

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
    private List<SubStepDto> subStepDtos;
}
