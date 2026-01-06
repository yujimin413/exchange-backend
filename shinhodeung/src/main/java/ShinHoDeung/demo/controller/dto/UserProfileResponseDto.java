package ShinHoDeung.demo.controller.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserProfileResponseDto {
    private Integer studentId;
    private String name;
    private String major;
    private String profileUrl;
}