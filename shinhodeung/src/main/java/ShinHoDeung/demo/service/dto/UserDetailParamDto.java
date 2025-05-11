package ShinHoDeung.demo.service.dto;

import java.util.ArrayList;
import java.util.HashMap;

import ShinHoDeung.demo.domain.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDetailParamDto {
    private User user;
    private ArrayList<String> interestedCountries;
    private Boolean skip;
    private HashMap<String, String> LanguageTests;
    private Float creditAverage;
    private Integer plannedGrade;
    private Integer plannedSemester;
}
