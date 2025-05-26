package ShinHoDeung.demo.service.dto;

import java.util.ArrayList;

import ShinHoDeung.demo.controller.dto.UniversityDto;
import ShinHoDeung.demo.controller.dto.UniversityFilterResponseDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UniversityFilterReturnDto {
    ArrayList<UniversityDto> universities;

    public UniversityFilterResponseDto toUniversityFilterResponseDto(){
        return UniversityFilterResponseDto.builder().universities(universities).build();
    }
}
