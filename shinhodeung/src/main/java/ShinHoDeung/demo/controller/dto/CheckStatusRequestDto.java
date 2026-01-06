package ShinHoDeung.demo.controller.dto;

import ShinHoDeung.demo.service.dto.ProgressCheckStatusParamDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckStatusRequestDto {
    private Boolean checked;

    public ProgressCheckStatusParamDto toProgressCheckStatusParamDto(Integer componentId){
        return ProgressCheckStatusParamDto.builder()
            .checked(checked)
            .componentId(componentId)
            .build();
    }
}
