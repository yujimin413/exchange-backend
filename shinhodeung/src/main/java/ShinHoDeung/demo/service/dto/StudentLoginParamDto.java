package ShinHoDeung.demo.service.dto;

import java.sql.Timestamp;

import org.jetbrains.annotations.NotNull;

import ShinHoDeung.demo.domain.Student;
import ShinHoDeung.demo.vo.JWTPayloadVo;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StudentLoginParamDto {
    private Integer id;
    private String name;
    private String major;

    @NotNull
    public JWTPayloadVo toJWTPayloadVO(){
        return JWTPayloadVo.builder()
                .studentId(this.id)
                .name(this.name)
                .major(this.major)
                .build();
    }

    @NotNull
    public Student toStudent(){
        return Student.builder()
                .id(this.id)
                .name(this.name)
                .major(this.major)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
