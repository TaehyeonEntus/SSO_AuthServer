package entus.authServer.service.authorization;

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

@Service
public class JwtGenerator {
    public JwtGenerator(@Value("${JWT_SECRET}") String secret, TokenRepository tokenRepository) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.tokenRepository = tokenRepository;
    }

    private final SecretKey secretKey;
    private final TokenRepository tokenRepository;

    public String generateAccessToken(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(10).toMillis()))//10분
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long userId) {
        String jwt = Jwts.builder()
                .setSubject(userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + Duration.ofDays(7).toMillis()))//7일
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        tokenRepository.save(userId.toString(),jwt);

        return jwt;
    }
}

