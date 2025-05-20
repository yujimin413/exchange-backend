package ShinHoDeung.demo.domain;

import org.jetbrains.annotations.NotNull;

import ShinHoDeung.demo.controller.dto.UserProfileResponseDto;
import ShinHoDeung.demo.vo.JWTPayloadVo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "student_id", nullable = false, unique = true)
    private Integer studentId;
    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;
    @Column(name = "major", nullable = false, length = 10)
    private String major;
    @Column(name = "profile_url", length = 2048)
    private String profileUrl;
    @Column(name = "credit_average")
    private Float creditAverage;
    @Column(name = "planned_grade")
    private Integer plannedGrade;
    @Column(name = "planned_semester")
    private Integer plannedSemester;
    @Column(name = "is_currently_enrolled")
    private Boolean isCurrentlyEnrolled;
    @Column(name = "current_semester")
    private String currentSemester;

    @NotNull
    public JWTPayloadVo toJWTPayloadVO(){
        return JWTPayloadVo.builder()
                .studentId(this.studentId)
                .name(this.userName)
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

    @NotNull
    public UserProfileResponseDto toUserProfileResponseDto(){
        return UserProfileResponseDto.builder()
                .studentId(this.studentId)
                .name(this.userName)
                .major(this.major)
                .build();
    }
}
