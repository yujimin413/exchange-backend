package ShinHoDeung.demo.service.dto;

import ShinHoDeung.demo.controller.dto.MypageResponseDto;
import ShinHoDeung.demo.controller.dto.MypageUpdateResponseDto;
import ShinHoDeung.demo.controller.dto.NameScore;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@Data
@Builder
public class MypageUpdateReturnDto {

    private Float creditAverage;
    private Integer plannedGrade;
    private Integer plannedSemester;
    private List<NameScore> languageScores;

    @NotNull
    public MypageUpdateResponseDto toMypageUpdateResponseDto(){
        return MypageUpdateResponseDto.builder()
                .creditAverage(this.creditAverage)
                .plannedGrade(this.plannedGrade)
                .plannedSemester(this.plannedSemester)
                .languageScores(this.languageScores)
                .build();
    }
}