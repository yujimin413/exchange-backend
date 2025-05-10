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
        return UserLoginParamDto.builder()
                .id(this.id)
                .name(this.name)
                .major(this.major)
                .build();
    }
}
