package ShinHoDeung.demo.service.dto;

import org.jetbrains.annotations.NotNull;

import ShinHoDeung.demo.controller.dto.StudentLoginResponseDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StudentLoginReturnDto {
    private String accessToken;
    private String refreshToken;
    private Integer studentId;
    private String name;
    private String major;

    @NotNull
    public StudentLoginResponseDto toStudentLoginResponseDto(){
        return StudentLoginResponseDto.builder()
                .accessToken(this.accessToken)
                .refreshToken(this.refreshToken)
                .studentId(this.studentId)
                .name(this.name)
                .major(this.major)
                .build();
    }
}
