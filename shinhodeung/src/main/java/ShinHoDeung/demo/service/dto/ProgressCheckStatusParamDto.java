package ShinHoDeung.demo.service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProgressCheckStatusParamDto {
    private Integer componentId;
    private Boolean checked;
}
