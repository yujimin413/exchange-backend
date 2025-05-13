package ShinHoDeung.demo.provider.dto;

import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class APIResponseDto {
    @NotNull
    private String body;

    @NotNull
    private Map<String, List<String>> headers;
}
