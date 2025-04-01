package ShinHoDeung.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ShinHoDeung.demo.controller.dto.StudentLoginRequestDto;
import ShinHoDeung.demo.service.AuthService;
import ShinHoDeung.demo.service.dto.UsaintAuthReturnDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StudentController {
    private final AuthService authService;
    
    @PostMapping("/login")
    public String login(@RequestBody StudentLoginRequestDto studentLoginRequestDto) {
        UsaintAuthReturnDto usaintAuthReturnDto;
        
        try{
            usaintAuthReturnDto = authService.uSaintAuth(studentLoginRequestDto.toUsaintAuthParamDto());
        } catch(Exception e){
            e.printStackTrace();
            return "";
        }
        
        String result = "";
        result = result + String.format("major : %s \n", usaintAuthReturnDto.getMajor());
        result = result + String.format("id : %s \n", usaintAuthReturnDto.getId());
        result = result + String.format("name : %s \n", usaintAuthReturnDto.getName());
        result = result + String.format("status : %s \n", usaintAuthReturnDto.getStatus());
        result = result + String.format("semester : %s \n", usaintAuthReturnDto.getSemester());
        
        return result;
    }
}
