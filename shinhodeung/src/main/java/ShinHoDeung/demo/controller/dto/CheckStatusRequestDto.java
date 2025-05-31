package ShinHoDeung.demo.controller.dto;

import ShinHoDeung.demo.service.dto.CheckStatusParamDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckStatusRequestDto {
    private Boolean checked;

    public CheckStatusParamDto toCheckStatusParamDto(Integer componentId){
        return CheckStatusParamDto.builder()
            .checked(checked)
            .componentId(componentId)
            .build();
    }
}
