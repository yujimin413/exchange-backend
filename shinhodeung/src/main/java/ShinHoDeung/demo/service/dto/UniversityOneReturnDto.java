package ShinHoDeung.demo.service.dto;

import java.util.ArrayList;

import ShinHoDeung.demo.controller.dto.UniversityDto;
import ShinHoDeung.demo.controller.dto.UniversityOneResponseDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UniversityOneReturnDto {
    ArrayList<UniversityDto> universities;

    public UniversityOneResponseDto toUniversityOneResponseDto(){
        return UniversityOneResponseDto.builder().universities(universities).build();
    }
}
