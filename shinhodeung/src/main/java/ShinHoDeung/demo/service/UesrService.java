package ShinHoDeung.demo.service;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import ShinHoDeung.demo.domain.RefreshToken;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.provider.TokenProvider;
import ShinHoDeung.demo.repository.RefreshTokenRepository;
import ShinHoDeung.demo.repository.UserRepository;
import ShinHoDeung.demo.service.dto.UserLoginParamDto;
import ShinHoDeung.demo.service.dto.UserLoginReturnDto;
import ShinHoDeung.demo.service.dto.UserValidateParamDto;
import ShinHoDeung.demo.service.dto.UserValidateReturnDto;
import ShinHoDeung.demo.vo.JWTPayloadVo;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UesrService {
    
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    @NotNull
    public UserLoginReturnDto userLogin(@NotNull UserLoginParamDto userLoginParamDto){
        Optional<User> userOptional = userRepository.findByStudentId(userLoginParamDto.getId());
        User user;

        if(userOptional.isEmpty()){
            user = userLoginParamDto.toUser();
        }
        else{
            user = userOptional.get();
            user.setStudentId(userLoginParamDto.getId());
            user.setMajor(userLoginParamDto.getMajor());
            user.setUserName(userLoginParamDto.getName());
        }
        userRepository.save(user);

        String accessToken = tokenProvider.generateAccessToken(userLoginParamDto.toJWTPayloadVO());
        String refreshToken = tokenProvider.generateRandomHashToken(50);

        RefreshToken refreshTokenDB = RefreshToken.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .studentId(user.getStudentId())
                .name(user.getUserName())
                .major(user.getMajor())
                .build();
        refreshTokenRepository.save(refreshTokenDB);

        return UserLoginReturnDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .studentId(user.getStudentId())
                .name(user.getUserName())
                .major(user.getMajor())
                .build();
    }

    @NotNull
    public UserValidateReturnDto validateUser(@NotNull UserValidateParamDto userValidateParamDto) throws Exception {
        JWTPayloadVo jwtPayloadVO;
        try {
            jwtPayloadVO = tokenProvider.validateAccessToken(userValidateParamDto.getAccessToken());
        } catch(BadCredentialsException e){
            log.warn(e.getMessage(), e);
            throw new Exception("Invalid access token.");
        } catch(ExpiredJwtException e){
            log.debug(e.getMessage(), e);

            Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findById(userValidateParamDto.getRefreshToken());
            if(refreshTokenOptional.isEmpty()){
                throw new Exception("Refresh token does not exist.");
            }

            RefreshToken refreshToken = refreshTokenOptional.get();
            if(!refreshToken.getAccessToken().equals(userValidateParamDto.getAccessToken())){
                throw new Exception("Access token and refresh token does not match.");
            }

            Optional<User> userOptional = userRepository.findById(refreshToken.getStudentId());
            if(userOptional.isEmpty()){
                throw new Exception("Student does not exist.");
            }

            User user = userOptional.get();

            if(!user.getMajor().equals(refreshToken.getMajor())){
                throw new Exception("Student major has changed.");
            }
            else if(!user.getUserName().equals(refreshToken.getName())){
                throw new Exception("Student name has changed.");
            }

            String newAccessToken = tokenProvider.generateAccessToken(user.toJWTPayloadVO());
            String newRefreshToken = tokenProvider.generateRandomHashToken(50);

            RefreshToken newRefreshTokenDB = RefreshToken.builder()
                    .refreshToken(newRefreshToken)
                    .accessToken(newAccessToken)
                    .studentId(user.getStudentId())
                    .name(user.getUserName())
                    .major(user.getMajor())
                    .build();

            refreshTokenRepository.save(newRefreshTokenDB);
            refreshTokenRepository.delete(refreshToken);

            return UserValidateReturnDto.builder()
                    .user(user)
                    .accessToken(Optional.of(newAccessToken))
                    .refreshToken(Optional.ofNullable(newRefreshToken))
                    .build();
        }

        Optional<User> user = userRepository.findById(jwtPayloadVO.getStudentId());
        if(user.isEmpty()){
            throw new Exception("Student does not exist.");
        }
        else if(!user.get().getMajor().equals(jwtPayloadVO.getMajor())){
            throw new Exception("Student major has changed.");
        }
        else if(!user.get().getUserName().equals(jwtPayloadVO.getName())){
            throw new Exception("Student name has changed.");
        }

        return UserValidateReturnDto.builder()
                .user(user.get())
                .accessToken(Optional.empty())
                .refreshToken(Optional.empty())
                .build();
    }
}
