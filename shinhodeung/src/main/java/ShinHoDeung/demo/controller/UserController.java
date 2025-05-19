package ShinHoDeung.demo.controller;

import ShinHoDeung.demo.controller.dto.MypageResponseDto;
import ShinHoDeung.demo.service.dto.MypageReturnDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import ShinHoDeung.demo.common.CommonResponse;
import ShinHoDeung.demo.common.StatusCode;
import ShinHoDeung.demo.controller.dto.UserDetailRequestDto;
import ShinHoDeung.demo.controller.dto.UserLoginRequestDto;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.exception.APIRequestFailedException;
import ShinHoDeung.demo.exception.AuthFailedException;
import ShinHoDeung.demo.exception.HTMLParseFailedException;
import ShinHoDeung.demo.service.AuthService;
import ShinHoDeung.demo.service.UserService;
import ShinHoDeung.demo.service.dto.UserLoginReturnDto;
import ShinHoDeung.demo.service.dto.UsaintAuthReturnDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final StatusCode statusCode;
    
    @NotNull
    @PostMapping("/login")
    public CommonResponse login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        UsaintAuthReturnDto usaintAuthReturnDto;
        
        try{
            usaintAuthReturnDto = authService.uSaintAuth(userLoginRequestDto.toUsaintAuthParamDto());
        } catch (AuthFailedException e) {
            return new CommonResponse(statusCode.SSU4010, null, statusCode.SSU4010_MSG);
        } catch (APIRequestFailedException | HTMLParseFailedException e) {
            return new CommonResponse(statusCode.SSU5000, null, statusCode.SSU5000_MSG);
        }

        UserLoginReturnDto userLoginReturnDto = userService.userLogin(usaintAuthReturnDto.toUserLoginParamDto());
        
        return new CommonResponse(statusCode.SSU2010, userLoginReturnDto.toUserLoginResponseDto(), statusCode.SSU2010_MSG);
    }

    @NotNull
    @PostMapping("/profile")
    public CommonResponse profile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return new CommonResponse(statusCode.SSU2020, user.toUserProfileResponseDto(), statusCode.SSU2020_MSG);
    }

    @NotNull
    @PostMapping("/add-detail")
    public CommonResponse addDetail(@RequestBody UserDetailRequestDto userDetailRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        
        userService.addUserDetail(userDetailRequestDto.toUserDetailParamDto(user));

        return new CommonResponse(statusCode.SSU2030, "success", statusCode.SSU2030_MSG);
    }

    @GetMapping("/mypage")
    public CommonResponse mypage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();


        System.out.println("authentication = " + authentication);
        System.out.println("principal = " + authentication.getPrincipal());


        MypageReturnDto mypageReturnDto = userService.getMypageInfo(user);

        return new CommonResponse(statusCode.SSU2000, mypageReturnDto.toMypageResponseDto(), statusCode.SSU2000_MSG);
    }


}
