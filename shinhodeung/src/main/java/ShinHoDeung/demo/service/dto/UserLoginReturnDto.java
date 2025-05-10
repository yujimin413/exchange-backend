package ShinHoDeung.demo.service.dto;

import org.jetbrains.annotations.NotNull;

import ShinHoDeung.demo.controller.dto.UserLoginResponseDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserLoginReturnDto {
    private String accessToken;
    private String refreshToken;
    private Integer studentId;
    private String name;
    private String major;

    @NotNull
    public UserLoginResponseDto toUserLoginResponseDto(){
        return UserLoginResponseDto.builder()
                .accessToken(this.accessToken)
                .refreshToken(this.refreshToken)
                .studentId(this.studentId)
                .name(this.name)
                .major(this.major)
                .build();
    }
}
