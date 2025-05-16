package ShinHoDeung.demo.service.dto;

import java.util.List;

import ShinHoDeung.demo.controller.dto.UniversityDetailResponseDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UniversityDetailReturnDto {
    private String koreanName;
    private String englishName;
    private boolean isFavorite;
    private String image;
    private String website;
    private List<String> tags;
    private String programType;
    private String institution;
    private String id;
    private String creditRequirement;
    private List<String> languageRequirement;
    private String region;
    private String country;
    private String languageRegion;
    private List<String> specialNotes;
    private List<String> notes;
    private List<String> availableCourses;
    private int reportNum;
    private List<String> reportNames;
    private List<String> aiSummary;

    public UniversityDetailResponseDto toUniversityDetailResponseDto(){
        return UniversityDetailResponseDto.builder()
                .koreanName(koreanName)
                .englishName(englishName)
                .isFavorite(isFavorite)
                .image(image)
                .website(website)
                .tags(tags)
                .programType(programType)
                .institution(institution)
                .id(id)
                .creditRequirement(creditRequirement)
                .languageRequirement(languageRequirement)
                .region(region)
                .country(country)
                .languageRegion(languageRegion)
                .specialNotes(specialNotes)
                .notes(notes)
                .availableCourses(availableCourses)
                .reportNum(reportNum)
                .reportNames(reportNames)
                .aiSummary(aiSummary)
                .build();
    }
}
