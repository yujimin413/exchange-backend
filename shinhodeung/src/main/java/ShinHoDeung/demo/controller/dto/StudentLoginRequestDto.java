package ShinHoDeung.demo.controller.dto;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import ShinHoDeung.demo.service.dto.UsaintAuthParamDto;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class StudentLoginRequestDto {
    @JsonProperty("sToken")
    @NotNull
    private String sToken;

    @JsonProperty("sIdno")
    @NotNull
    private Integer sIdno;

    public UsaintAuthParamDto toUsaintAuthParamDto(){
        return UsaintAuthParamDto.builder()
                .sToken(this.sToken)
                .sIdno(this.sIdno)
                .build();
    }
}