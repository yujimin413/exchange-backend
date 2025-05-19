package ShinHoDeung.demo.controller.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MypageResponseDto {

    // 학적 정보
    private String profileUrl;
    private Integer studentId;
    private String name;
    private Boolean isCurrentlyEnrolled;  // 재학 여부
    private String currentSemester; // 현재 학기

    // 지원 자격
    private Float creditAverage;
    private Integer plannedGrade; // 교환 예정 학년
    private Integer plannedSemester; // 교환 예정 학기
    private List<NameScore> languageScores;
}
