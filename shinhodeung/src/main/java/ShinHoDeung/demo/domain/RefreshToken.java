package ShinHoDeung.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

// import org.springframework.data.annotation.Id;
// import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@RedisHash(value = "refreshToken", timeToLive = 3600 * 24 * 120L)
public class RefreshToken {
    @Id
    private String refreshToken;
    private String accessToken;
    private Integer studentId;
    private String name;
    private String major;
}