package ShinHoDeung.demo.controller.dto;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UniversityOneResponseDto {
    ArrayList<UniversityDto> universities;
}
