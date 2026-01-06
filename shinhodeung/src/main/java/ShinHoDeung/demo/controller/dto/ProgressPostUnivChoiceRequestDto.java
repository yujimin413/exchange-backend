package ShinHoDeung.demo.controller.dto;

import java.util.List;

import ShinHoDeung.demo.service.dto.ProgressPostUnivChoiceParamDto;
import lombok.Data;

@Data
public class ProgressPostUnivChoiceRequestDto {
    List<UnivChoiceDto> univs;

    public ProgressPostUnivChoiceParamDto tProgressPostUnivChoiceParamDto(){
        return ProgressPostUnivChoiceParamDto.builder()
            .univs(univs)
            .build();
    }
}
