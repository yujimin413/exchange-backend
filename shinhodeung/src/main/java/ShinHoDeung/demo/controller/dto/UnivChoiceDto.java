package ShinHoDeung.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UnivChoiceDto {
    @JsonProperty("university_id")
    private String univId;
    private Integer order;
}
