package ShinHoDeung.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import ShinHoDeung.demo.domain.progress.ContentType;
import ShinHoDeung.demo.service.dto.ProgressCustomBoxParamDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProgressCustomBoxRequestDto {
    private String content;
    @JsonProperty("type")
    private ContentType contentType;

    public ProgressCustomBoxParamDto toProgressCustomBoxParamDto(Integer componentId){
        return ProgressCustomBoxParamDto.builder()
            .componentId(componentId)
            .content(content)
            .contentType(contentType)
            .build();
    }
}
