package ShinHoDeung.demo.controller.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StudentLoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private Integer studentId;
    private String name;
    private String major;
}