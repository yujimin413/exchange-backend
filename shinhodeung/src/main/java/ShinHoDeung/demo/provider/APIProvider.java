package ShinHoDeung.demo.provider;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

import org.springframework.stereotype.Component;

import ShinHoDeung.demo.provider.dto.APIRequestDto;
import ShinHoDeung.demo.provider.dto.APIResponseDto;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class APIProvider {
    public APIResponseDto get(APIRequestDto apiRequestDto) throws Exception {
        
        String urlString = apiRequestDto.getUrl();
        Map<String, String> headers = apiRequestDto.getHeaders();
        
        HttpClient client = HttpClient.newHttpClient();
        java.net.http.HttpRequest.Builder builder = HttpRequest.newBuilder()
            .uri(URI.create(urlString))
            .GET();
        for(String key : headers.keySet())
            builder.header(key, headers.get(key));
        HttpRequest request = builder.build();
    
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        
        return APIResponseDto.builder()
                .body(response.body())
                .headers(response.headers().map())
                .build();
    }
}