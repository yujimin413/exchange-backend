package ShinHoDeung.demo.util;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class HTTPRequestUtil {
    
    @NotNull
    public Optional<String> getAccessToken(@NotNull HttpServletRequest request) {
        final String HEADER_KEY = "Authorization";
        final String PREFIX = "Bearer ";

        Optional<String> token = Optional.ofNullable(request.getHeader(HEADER_KEY));

        if (token.isPresent() && token.get().startsWith(PREFIX)) {
            return Optional.of(token.get().substring(PREFIX.length()));
        }

        return Optional.empty();
    }

    @NotNull
    public Optional<String> getRefreshToken(@NotNull HttpServletRequest request){
        final String HEADER_KEY = "Refresh-Token";
        return Optional.ofNullable(request.getHeader(HEADER_KEY));
    }

    @NotNull
    public Optional<String> getClientKey(@NotNull HttpServletRequest request){
        final String HEADER_KEY = "Client-Key";
        return Optional.ofNullable(request.getHeader(HEADER_KEY));
    }
}
