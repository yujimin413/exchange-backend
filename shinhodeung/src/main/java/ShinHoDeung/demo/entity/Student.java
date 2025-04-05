package ShinHoDeung.demo.entity;

import java.sql.Timestamp;

import org.jetbrains.annotations.NotNull;

import ShinHoDeung.demo.vo.JWTPayloadVo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "major", nullable = false, length = 10)
    private String major;
    // @Column(name = "xnApiToken", nullable = true, length = 500)
    // private String xnApiToken;
    @Column(name = "createdAt", nullable = false)
    private Timestamp createdAt;
    @Column(name = "updatedAt", nullable = true)
    private Timestamp updatedAt;

    @NotNull
    public JWTPayloadVo toJWTPayloadVO(){
        return JWTPayloadVo.builder()
                .studentId(this.id)
                .name(this.name)
                .major(this.major)
                .build();
    }

    // @NotNull
    // public StudentLogoutParamDto toStudentLogoutParamDto(@NotNull String refreshToken){
    //     return StudentLogoutParamDto.builder()
    //             .student(this)
    //             .refreshToken(refreshToken)
    //             .build();
    // }

    // @NotNull
    // public StudentProfileResponseDto toStudentProfileResponseDto(){
    //     return StudentProfileResponseDto.builder()
    //             .studentId(this.id)
    //             .name(this.name)
    //             .major(this.major)
    //             .build();
    // }
}
