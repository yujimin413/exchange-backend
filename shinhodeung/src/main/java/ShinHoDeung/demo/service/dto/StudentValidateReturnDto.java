package ShinHoDeung.demo.service.dto;

import java.util.Optional;

import ShinHoDeung.demo.domain.Student;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StudentValidateReturnDto {
    private Student student;
    private Optional<String> accessToken;
    private Optional<String> refreshToken;
}

