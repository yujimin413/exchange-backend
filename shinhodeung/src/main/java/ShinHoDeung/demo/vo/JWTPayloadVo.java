package ShinHoDeung.demo.vo;

import org.jetbrains.annotations.NotNull;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JWTPayloadVo {
    @NotNull
    private Integer studentId;
    @NotNull
    private String name;
    @NotNull
    private String major;
}