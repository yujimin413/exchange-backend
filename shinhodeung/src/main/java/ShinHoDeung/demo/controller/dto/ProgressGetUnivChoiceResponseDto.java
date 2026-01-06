package ShinHoDeung.demo.controller.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProgressGetUnivChoiceResponseDto {
    private List<UnivChoiceDto> univs;
}
