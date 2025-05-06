package ShinHoDeung.demo.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import ShinHoDeung.demo.domain.RefreshToken;
import ShinHoDeung.demo.domain.Student;
import ShinHoDeung.demo.provider.TokenProvider;
import ShinHoDeung.demo.repository.RefreshTokenRepository;
import ShinHoDeung.demo.repository.StudentRepository;
import ShinHoDeung.demo.service.dto.StudentLoginParamDto;
import ShinHoDeung.demo.service.dto.StudentLoginReturnDto;
import ShinHoDeung.demo.service.dto.StudentValidateParamDto;
import ShinHoDeung.demo.service.dto.StudentValidateReturnDto;
import ShinHoDeung.demo.vo.JWTPayloadVo;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    @NotNull
    public StudentLoginReturnDto studentLogin(@NotNull StudentLoginParamDto studentLoginParamDto){
        Optional<Student> studentOptional = studentRepository.findById(studentLoginParamDto.getId());
        Student student;

        if(studentOptional.isEmpty()){
            student = studentLoginParamDto.toStudent();
        }
        else{
            student = studentOptional.get();
            student.setMajor(studentLoginParamDto.getMajor());
            student.setName(studentLoginParamDto.getName());
            student.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        }
        studentRepository.save(student);

        String accessToken = tokenProvider.generateAccessToken(studentLoginParamDto.toJWTPayloadVO());
        String refreshToken = tokenProvider.generateRandomHashToken(50);

        RefreshToken refreshTokenDB = RefreshToken.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .studentId(student.getId())
                .name(student.getName())
                .major(student.getMajor())
                .build();
        refreshTokenRepository.save(refreshTokenDB);

        return StudentLoginReturnDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .studentId(student.getId())
                .name(student.getName())
                .major(student.getMajor())
                .build();
    }

    @NotNull
    public StudentValidateReturnDto validateStudent(@NotNull StudentValidateParamDto studentValidateParamDto) throws Exception {
        JWTPayloadVo jwtPayloadVO;
        try {
            jwtPayloadVO = tokenProvider.validateAccessToken(studentValidateParamDto.getAccessToken());
        } catch(BadCredentialsException e){
            log.warn(e.getMessage(), e);
            throw new Exception("Invalid access token.");
        } catch(ExpiredJwtException e){
            log.debug(e.getMessage(), e);

            Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findById(studentValidateParamDto.getRefreshToken());
            if(refreshTokenOptional.isEmpty()){
                throw new Exception("Refresh token does not exist.");
            }

            RefreshToken refreshToken = refreshTokenOptional.get();
            if(!refreshToken.getAccessToken().equals(studentValidateParamDto.getAccessToken())){
                throw new Exception("Access token and refresh token does not match.");
            }

            Optional<Student> studentOptional = studentRepository.findById(refreshToken.getStudentId());
            if(studentOptional.isEmpty()){
                throw new Exception("Student does not exist.");
            }

            Student student = studentOptional.get();

            if(!student.getMajor().equals(refreshToken.getMajor())){
                throw new Exception("Student major has changed.");
            }
            else if(!student.getName().equals(refreshToken.getName())){
                throw new Exception("Student name has changed.");
            }

            String newAccessToken = tokenProvider.generateAccessToken(student.toJWTPayloadVO());
            String newRefreshToken = tokenProvider.generateRandomHashToken(50);

            RefreshToken newRefreshTokenDB = RefreshToken.builder()
                    .refreshToken(newRefreshToken)
                    .accessToken(newAccessToken)
                    .studentId(student.getId())
                    .name(student.getName())
                    .major(student.getMajor())
                    .build();

            refreshTokenRepository.save(newRefreshTokenDB);
            refreshTokenRepository.delete(refreshToken);

            return StudentValidateReturnDto.builder()
                    .student(student)
                    .accessToken(Optional.of(newAccessToken))
                    .refreshToken(Optional.ofNullable(newRefreshToken))
                    .build();
        }

        Optional<Student> student = studentRepository.findById(jwtPayloadVO.getStudentId());
        if(student.isEmpty()){
            throw new Exception("Student does not exist.");
        }
        else if(!student.get().getMajor().equals(jwtPayloadVO.getMajor())){
            throw new Exception("Student major has changed.");
        }
        else if(!student.get().getName().equals(jwtPayloadVO.getName())){
            throw new Exception("Student name has changed.");
        }

        return StudentValidateReturnDto.builder()
                .student(student.get())
                .accessToken(Optional.empty())
                .refreshToken(Optional.empty())
                .build();
    }
}
