package ShinHoDeung.demo.service.dto;

import java.util.List;

import ShinHoDeung.demo.controller.dto.MypageResponseDto;
import ShinHoDeung.demo.controller.dto.NameScore;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
public class MypageReturnDto {
    // 학적정보
    private String profileUrl;
    private Integer studentId;
    private String name;
    private String studentStatus;
    private String currentSemester;
    private String major;

    // 지원자격
    private Float creditAverage;
    private Integer plannedGrade;
    private Integer plannedSemester;
    private List<NameScore> languageScores;

    @NotNull
    public MypageResponseDto toMypageResponseDto(){
        return MypageResponseDto.builder()
                .profileUrl(this.profileUrl)
                .studentId(this.studentId)
                .name(this.name)
                .studentStatus(this.studentStatus)
                .currentSemester(this.currentSemester)
                .creditAverage(this.creditAverage)
                .plannedGrade(this.plannedGrade)
                .plannedSemester(this.plannedSemester)
                .languageScores(this.languageScores)
                .major(this.major)
                .build();
    }
}