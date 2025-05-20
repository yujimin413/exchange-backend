package ShinHoDeung.demo.controller.dto;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@Data
@Builder
public class MypageUpdateResponseDto {

    private String profileUrl;
    private Integer plannedGrade;
    private Integer plannedSemester;

    @NotNull
    private List<NameScore> languageScores;
}
