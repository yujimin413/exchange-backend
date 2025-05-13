package ShinHoDeung.demo.controller.dto;

import java.util.ArrayList;
import java.util.HashMap;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.service.dto.UserDetailParamDto;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class UserDetailRequestDto {
    @JsonProperty("interestedCountries")
    @NotNull
    private ArrayList<String> interestedCountries;

    @JsonProperty("skip")
    @NotNull
    private Boolean skip;
    
    @JsonProperty("languageTests")
    private ArrayList<NameScore> languageTests;
    
    @JsonProperty("creditAverage")
    private Float creditAverage;
    
    @JsonProperty("plannedGrade")
    private Integer plannedGrade;

    @JsonProperty("plannedSemester")
    private Integer plannedSemester;
    

    public UserDetailParamDto toUserDetailParamDto(User user){
        HashMap<String,String> map = new HashMap<String,String>();
        if(languageTests != null)
            for(NameScore test : languageTests)
                    map.put(test.testName, test.testScore);

        return UserDetailParamDto.builder()
        .user(user)
        .interestedCountries(interestedCountries)
        .skip(skip)
        .LanguageTests(map)
        .creditAverage(creditAverage)
        .plannedGrade(plannedGrade)
        .plannedSemester(plannedSemester)
        .build();
    }
}

