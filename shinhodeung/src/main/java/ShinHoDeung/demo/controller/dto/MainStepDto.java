package ShinHoDeung.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.*;

@Builder
@Data
public class MainStepDto {

    @JsonProperty("main-step-name")
    private String title;

    @JsonProperty("main-step-num")
    private int sortOrder;

    @JsonProperty("sub-steps")
    private List<SubStepDto> subStepDtos;
}
