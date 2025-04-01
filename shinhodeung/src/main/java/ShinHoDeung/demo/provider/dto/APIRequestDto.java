package ShinHoDeung.demo.provider.dto;

import java.util.Map;

import org.jetbrains.annotations.NotNull;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class APIRequestDto {
    @NotNull
    private String url;

    @NotNull
    private Map<String, String> headers;
}
