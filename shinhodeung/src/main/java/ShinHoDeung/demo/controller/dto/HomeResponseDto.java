package ShinHoDeung.demo.controller.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HomeResponseDto {
    private List<UnivChoiceDto> univs;
    private MainStepDto progress;
    private List<ComponentDto> components;
}
