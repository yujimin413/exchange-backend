package ShinHoDeung.demo.controller.dto;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UniversityLikeRequestDto {
    @JsonProperty("universityId")
    @NotNull
    String universityId;
}
