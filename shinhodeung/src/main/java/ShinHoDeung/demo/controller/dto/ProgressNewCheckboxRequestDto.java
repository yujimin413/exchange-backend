package ShinHoDeung.demo.controller.dto;

import ShinHoDeung.demo.service.dto.ProgressNewCheckboxParamDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProgressNewCheckboxRequestDto {
    private String content;

    public ProgressNewCheckboxParamDto toProgressNewCheckboxParamDto(Integer componentId){
        return ProgressNewCheckboxParamDto.builder()
            .componentId(componentId)
            .content(content)
            .build();
    }
}
