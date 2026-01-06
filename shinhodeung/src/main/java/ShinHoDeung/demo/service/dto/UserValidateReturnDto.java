package ShinHoDeung.demo.service.dto;

import java.util.Optional;

import ShinHoDeung.demo.domain.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserValidateReturnDto {
    private User user;
    private Optional<String> accessToken;
    private Optional<String> refreshToken;
}

