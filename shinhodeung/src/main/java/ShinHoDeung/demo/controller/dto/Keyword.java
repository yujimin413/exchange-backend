package ShinHoDeung.demo.controller.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Keyword {
    private String type;
    private String name;
    private Detail detail;
}
