package ShinHoDeung.demo.service.dto;

import java.util.List;

import ShinHoDeung.demo.controller.dto.ComponentDto;
import ShinHoDeung.demo.controller.dto.HomeResponseDto;
import ShinHoDeung.demo.controller.dto.MainStepDto;
import ShinHoDeung.demo.controller.dto.UnivChoiceDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HomeReturnDto {
    private List<UnivChoiceDto> univs;
    private MainStepDto progress;
    private List<ComponentDto> components;

    public HomeResponseDto toHomeResponseDto(){
        return HomeResponseDto.builder()
            .univs(univs)
            .progress(progress)
            .components(components)
            .build();
    }
}
