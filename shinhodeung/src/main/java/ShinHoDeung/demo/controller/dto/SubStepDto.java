package ShinHoDeung.demo.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Data
public class SubStepDto {
    @JsonProperty("sub_step_name")
    private String title;

    @JsonProperty("sub_step_num")
    private int sortOrder;

    @JsonProperty("details")
    private List<DetailDto> detailDtos;
}
