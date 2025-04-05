package ShinHoDeung.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ShinHoDeung.demo.common.CommonResponse;
import ShinHoDeung.demo.common.StatusCode;
import ShinHoDeung.demo.controller.dto.StudentLoginRequestDto;
import ShinHoDeung.demo.exception.APIRequestFailedException;
import ShinHoDeung.demo.exception.AuthFailedException;
import ShinHoDeung.demo.exception.HTMLParseFailedException;
import ShinHoDeung.demo.service.AuthService;
import ShinHoDeung.demo.service.StudentService;
import ShinHoDeung.demo.service.dto.StudentLoginReturnDto;
import ShinHoDeung.demo.service.dto.UsaintAuthReturnDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final AuthService authService;
    private final StatusCode statusCode;
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody StudentLoginRequestDto studentLoginRequestDto) {
        UsaintAuthReturnDto usaintAuthReturnDto;
        
        try{
            usaintAuthReturnDto = authService.uSaintAuth(studentLoginRequestDto.toUsaintAuthParamDto());
        } catch (AuthFailedException e) {
            return new CommonResponse(statusCode.SSU4010, null, statusCode.SSU4010_MSG).toResponseEntity();
        } catch (APIRequestFailedException | HTMLParseFailedException e) {
            return new CommonResponse(statusCode.SSU5000, null, statusCode.SSU5000_MSG).toResponseEntity();
        }

        StudentLoginReturnDto studentLoginReturnDto = studentService.studentLogin(usaintAuthReturnDto.toStudentLoginParamDto());
        
        return new CommonResponse(statusCode.SSU2010, studentLoginReturnDto.toStudentLoginResponseDto(), statusCode.SSU2010_MSG).toResponseEntity();
    }
}
