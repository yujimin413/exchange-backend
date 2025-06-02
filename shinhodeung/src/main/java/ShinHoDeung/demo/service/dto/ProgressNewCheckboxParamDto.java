package ShinHoDeung.demo.service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProgressNewCheckboxParamDto {
    private Integer componentId;
    private String content;
}
