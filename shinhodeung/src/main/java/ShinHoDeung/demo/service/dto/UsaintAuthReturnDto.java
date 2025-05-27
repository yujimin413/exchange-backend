package ShinHoDeung.demo.service.dto;

import org.jetbrains.annotations.NotNull;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsaintAuthReturnDto {
    private Integer id;
    private String name;
    private String major;
    private String status;
    private String semester;

    @NotNull
    public UserLoginParamDto toUserLoginParamDto(){
        String simplifiedStatus = (this.status != null && this.status.contains(" "))
                ? this.status.trim().substring(this.status.lastIndexOf(" ") + 1)
                : this.status; // 공백이 없을 경우 전체를 그대로 사용

        return UserLoginParamDto.builder()
                .id(this.id)
                .name(this.name)
                .major(this.major)
                .isCurrentlyEnrolled(simplifiedStatus)
                .semester(this.semester)
                .build();
    }
}
