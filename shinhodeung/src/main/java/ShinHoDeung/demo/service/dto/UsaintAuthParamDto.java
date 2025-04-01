package ShinHoDeung.demo.service.dto;

import org.jetbrains.annotations.NotNull;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsaintAuthParamDto {
    @NotNull
    private String sToken;
    @NotNull
    private Integer sIdno;
}