package ShinHoDeung.demo.controller.dto;

import lombok.Getter;

@Getter
public class ProgressAddCheckboxResponseDto {
    private Integer componentId;

    public ProgressAddCheckboxResponseDto(Integer componentId){
        this.componentId = componentId;
    }
}
