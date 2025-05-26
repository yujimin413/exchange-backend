package ShinHoDeung.demo.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Detail {
    @JsonInclude(JsonInclude.Include.NON_NULL)  
    private Integer university_count;  // country에만 해당
    
    private String region;

    @JsonInclude(JsonInclude.Include.NON_NULL) 
    private String english_name;  // university에만 해당

    @JsonInclude(JsonInclude.Include.NON_NULL) 
    private String country;  // university에만 해당

    @JsonInclude(JsonInclude.Include.NON_NULL)  
    private List<String> tags;  // university에만 해당

}
