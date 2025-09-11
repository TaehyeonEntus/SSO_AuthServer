package entus.authServer.service.authorization;

import entus.authServer.exception.AccessTokenException;
import entus.authServer.exception.RefreshTokenException;
import entus.authServer.repository.TokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * 토큰 검증기
 */
@Service
public class JwtValidator {
    private final SecretKey secretKey;
    private final TokenRepository tokenRepository;

    public JwtValidator(TokenRepository tokenRepository,
                        @Value("${JWT_SECRET}") String jwtSecret) {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.tokenRepository = tokenRepository;
    }

    public String validateRefreshToken(String refreshToken) throws RefreshTokenException {
        String userId = null;

        try {
            userId = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(refreshToken)
                    .getPayload()
                    .getSubject();
        } catch (JwtException e) {
            throw new RefreshTokenException(e.getMessage());
        }

        // InvalidTokenException
        if (!tokenRepository.existsByUserId(userId))
            throw new RefreshTokenException("token does not exist");

        return userId;
    }

    public Jws<Claims> validateAccessToken(String accessToken) throws AccessTokenException {
        Jws<Claims> jws;

        try {
            jws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(accessToken);
        } catch (JwtException e) {
            throw new AccessTokenException(e.getMessage());
        }

        return jws;
    }
}
