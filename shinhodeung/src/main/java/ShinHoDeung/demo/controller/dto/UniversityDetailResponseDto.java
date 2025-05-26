package ShinHoDeung.demo.controller.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UniversityDetailResponseDto {
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
}
