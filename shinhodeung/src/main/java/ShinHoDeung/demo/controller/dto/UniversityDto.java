package ShinHoDeung.demo.controller.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UniversityDto {
    private String id;
    private String koreanName;
    private String englishName;
    private List<String> notes;
    private List<String> tags;
    private String image;
    private boolean isFavorite;    
}
