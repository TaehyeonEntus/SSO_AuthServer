package entus.authServer.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

/**
 * Redis 토큰 관리
 */
@Repository
@RequiredArgsConstructor
public class TokenRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public void save(String userId, String refreshToken) {
        redisTemplate.opsForValue().set(userId, refreshToken, Duration.ofDays(7));
    }

    public String findByUserId(String userId) {
        return redisTemplate.opsForValue().get(userId);
    }

    public void deleteByUserId(String userId) {
        redisTemplate.delete(userId);
    }

    public boolean existsByUserId(String userId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(userId));
    }
}
