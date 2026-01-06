package ShinHoDeung.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MypageUpdateRequestDto {

    private Float creditAverage;
    private Integer plannedGrade;
    private Integer plannedSemester;

    @NotNull
    private List<NameScore> languageScores;
}
