package ShinHoDeung.demo.service.dto;

import java.util.List;

import ShinHoDeung.demo.controller.dto.LangaugeSelection;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UniversityFilterParamDto {
    List<String> regions;
    List<String> programTypes;
    List<String> availableMajors;
    List<LangaugeSelection> languageSelections;
}
