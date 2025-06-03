package ShinHoDeung.demo.service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProgressNewStatusParamDto {
    private Integer mainStepOrder;
    private Integer subStepOrder;
}
