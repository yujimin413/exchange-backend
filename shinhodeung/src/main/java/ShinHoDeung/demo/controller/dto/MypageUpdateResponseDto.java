package ShinHoDeung.demo.controller.dto;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@Data
@Builder
public class MypageUpdateResponseDto {

    private Float creditAverage;
    private Integer plannedGrade;
    private Integer plannedSemester;

    @NotNull
    private List<NameScore> languageScores;
}
