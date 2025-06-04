package ShinHoDeung.demo.controller.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnivChoiceDto {
    @JsonProperty("university_id")
    private String univId;
    private Integer order;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
