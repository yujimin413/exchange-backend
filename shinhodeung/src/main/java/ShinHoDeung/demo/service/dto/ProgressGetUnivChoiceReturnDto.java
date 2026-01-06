package ShinHoDeung.demo.service.dto;

import java.util.List;

import ShinHoDeung.demo.controller.dto.ProgressGetUnivChoiceResponseDto;
import ShinHoDeung.demo.controller.dto.UnivChoiceDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProgressGetUnivChoiceReturnDto {
    private List<UnivChoiceDto> univs;

    public ProgressGetUnivChoiceResponseDto toProgressGetUnivChoiceResponseDto(){
        return ProgressGetUnivChoiceResponseDto.builder()
            .univs(univs)
            .build();
    }
}
