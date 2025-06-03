package ShinHoDeung.demo.service.dto;

import java.time.LocalDate;

import ShinHoDeung.demo.domain.progress.ContentType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProgressUpdateComponentParamDto {
    private Integer componentId;
    private ContentType contentType;
    private String content;
    private String note;
    private LocalDate dueAt;
}
