package ShinHoDeung.demo.service.dto;

import java.util.List;

import ShinHoDeung.demo.controller.dto.UnivChoiceDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProgressPostUnivChoiceParamDto {
    List<UnivChoiceDto> univs;
}
