package ShinHoDeung.demo.service.dto;

import java.util.ArrayList;

import ShinHoDeung.demo.controller.dto.UniversityAllResponseDto;
import ShinHoDeung.demo.controller.dto.UniversityDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UniversityAllReturnDto {
    ArrayList<UniversityDto> universities;

    public UniversityAllResponseDto toUniversityAllResponseDto(){
        return UniversityAllResponseDto.builder().universities(universities).build();
    }
}
