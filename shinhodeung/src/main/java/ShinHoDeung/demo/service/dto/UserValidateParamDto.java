package ShinHoDeung.demo.service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserValidateParamDto {
    private String accessToken;
    private String refreshToken;
}
