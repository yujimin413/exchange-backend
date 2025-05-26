package ShinHoDeung.demo.controller.dto;

import java.util.List;

import ShinHoDeung.demo.service.dto.UniversityFilterParamDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UniversityFilterRequestDto {
    List<String> regions;
    List<String> countries;
    List<String> programTypes;
    List<String> availableMajors;
    List<LangaugeSelection> languageSelections;

    public UniversityFilterParamDto toUniversityFilterParamDto(){
        return UniversityFilterParamDto.builder()
                .regions(regions)
                .countries(countries)
                .programTypes(programTypes)
                .availableMajors(availableMajors)
                .languageSelections(languageSelections)
                .build();
    }
}
