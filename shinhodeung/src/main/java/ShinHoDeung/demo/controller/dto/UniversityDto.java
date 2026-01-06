package ShinHoDeung.demo.controller.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UniversityDto {
    private String id;
    private String koreanName;
    private String englishName;
    private String region;
    private String country;
    private List<String> notes;
    private List<String> tags;
    private String image;
    private boolean isFavorite;    
    private BigDecimal latitude;
    private BigDecimal longitude;
}
