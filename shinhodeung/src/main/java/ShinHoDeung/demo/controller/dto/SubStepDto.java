package ShinHoDeung.demo.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Data
public class SubStepDto {
    @JsonProperty("sub-step-name")
    private String title;

    @JsonProperty("sub-step-num")
    private int sortOrder;

    @JsonProperty("details")
    private List<DetailDto> detailDtos;
}
