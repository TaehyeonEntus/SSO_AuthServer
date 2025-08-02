package entus.authServer.service.authorization;

import entus.authServer.exception.InvalidTokenException;
import entus.authServer.repository.TokenRepository;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class JwtValidator {
    private final SecretKey secretKey;
    private final TokenRepository tokenRepository;

    public JwtValidator(TokenRepository tokenRepository) {
        Dotenv dotenv = Dotenv
                .load();
        String secret = dotenv.get("JWT_SECRET");

        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.tokenRepository = tokenRepository;
    }

    public String validateToken(String refreshToken) throws InvalidTokenException, ExpiredJwtException, SignatureException {
        // ExpiredJwtException, SignatureException
        String userId = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload()
                .getSubject();

        // InvalidTokenException
        if (!tokenRepository.existsByUserId(userId))
            throw new InvalidTokenException("유효하지 않은 토큰입니다");

        return userId;
    }
}
