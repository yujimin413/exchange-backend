package ShinHoDeung.demo.service.dto;

import org.jetbrains.annotations.NotNull;

import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.vo.JWTPayloadVo;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserLoginParamDto {
    private Integer id;
    private String name;
    private String major;
    private String studentStatus;
    private String semester;

    @NotNull
    public JWTPayloadVo toJWTPayloadVO(){
        return JWTPayloadVo.builder()
                .studentId(this.id)
                .name(this.name)
                .major(this.major)
                .build();
    }

    @NotNull
    public User toUser(){
        
        return User.builder()
                .studentId(this.id)
                .userName(this.name)
                .major(this.major)
                .studentStatus(this.studentStatus)
                .currentSemester(this.semester)
                .build();
    }
}
