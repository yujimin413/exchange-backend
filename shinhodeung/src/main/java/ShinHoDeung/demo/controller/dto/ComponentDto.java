package ShinHoDeung.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import ShinHoDeung.demo.domain.progress.ContentType;
import lombok.*;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComponentDto {
    @JsonProperty("component-id")
    private Integer componentId;

    @JsonProperty("custom-id")
    private Integer customId;
    
    @JsonProperty("type")
    private ContentType contentType;

    private String content;

    private String note;

    private Boolean checked;
}
