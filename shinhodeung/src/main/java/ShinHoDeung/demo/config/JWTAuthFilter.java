package ShinHoDeung.demo.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ShinHoDeung.demo.service.UserService;
import ShinHoDeung.demo.service.dto.UserValidateParamDto;
import ShinHoDeung.demo.service.dto.UserValidateReturnDto;
import ShinHoDeung.demo.util.HTTPRequestUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthFilter extends OncePerRequestFilter {
    /**
     * DI
     */
    private final UserService userService;
    private final HTTPRequestUtil httpRequestUtil;

    /**
     * Do filter
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            Optional<String> accessToken = httpRequestUtil.getAccessToken(request);
            Optional<String> refreshToken = httpRequestUtil.getRefreshToken(request);
            if (accessToken.isEmpty()) {
                throw new Exception("No access token provided.");
            }
            if (refreshToken.isEmpty()) {
                throw new Exception("No refresh token provided.");
            }

            UserValidateParamDto userValidateParamDto = UserValidateParamDto.builder()
                    .accessToken(accessToken.get())
                    .refreshToken(refreshToken.get())
                    .build();

            UserValidateReturnDto userValidateReturnDTO = userService.validateUser(userValidateParamDto);
            if(userValidateReturnDTO.getAccessToken().isPresent()){
                response.addHeader("Access-Token", userValidateReturnDTO.getAccessToken().get());
            }
            if(userValidateReturnDTO.getRefreshToken().isPresent()){
                response.addHeader("Refresh-Token", userValidateReturnDTO.getRefreshToken().get());
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(userValidateReturnDTO.getUser(), "", List.of());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        filterChain.doFilter(request, response);
    }

}