package ShinHoDeung.demo.controller.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import ShinHoDeung.demo.domain.progress.ContentType;
import ShinHoDeung.demo.service.dto.ProgressUpdateComponentParamDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProgressUpdateComponentRequestDto {
    @JsonProperty("type")
    private ContentType contentType;
    private String content;
    private String note;
    @JsonProperty("due_at")
    private LocalDate dueAt;

    public ProgressUpdateComponentParamDto toProgressDatePlusParamDto(Integer componentId){
        return ProgressUpdateComponentParamDto.builder()
            .componentId(componentId)
            .contentType(contentType)
            .content(content)
            .note(note)
            .dueAt(dueAt)
            .build();
    }
}
