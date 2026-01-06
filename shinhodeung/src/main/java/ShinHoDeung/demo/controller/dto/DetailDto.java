package ShinHoDeung.demo.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Builder
@Data
public class DetailDto {
    private String title;
    @JsonProperty("detail_id")
    private Integer detailId;

    @JsonProperty("components")
    private List<ComponentDto> componentDtos;
}
