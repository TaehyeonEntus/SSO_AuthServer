package entus.authServer.service.authorization;

import entus.authServer.domain.user.User;
import entus.authServer.repository.TokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

/**
 * 토큰 생성기
 * accessToken 기본 expireTime -> 10분
 * refreshToken 기본 expireTime -> 7일
 */
@Service
public class JwtGenerator {
    private final SecretKey secretKey;
    private final TokenRepository tokenRepository;

    public JwtGenerator(TokenRepository tokenRepository,
                        @Value("${JWT_SECRET}") String jwtSecret) {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.tokenRepository = tokenRepository;
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .claim("sub", user.getId().toString())
                .claim("name", user.getName())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(10).toMillis()))//10분
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user) {
        String jwt = Jwts.builder()
                .claim("sub", user.getId().toString())
                .claim("name", user.getName())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + Duration.ofDays(7).toMillis()))//7일
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        //Refresh Token은 생성과 동시에 Redis에 저장
        tokenRepository.save(user.getId().toString(), jwt);
        return jwt;
    }
}

