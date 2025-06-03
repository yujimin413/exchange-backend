package ShinHoDeung.demo.service.dto;

import ShinHoDeung.demo.domain.progress.ContentType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProgressCustomBoxParamDto {
    private Integer componentId;
    private String content;
    private ContentType contentType;
}
