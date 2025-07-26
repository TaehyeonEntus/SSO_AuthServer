package entus.authServer.service.authorization;

import entus.authServer.domain.token.Token;
import entus.authServer.exception.InvalidTokenException;
import entus.authServer.repository.TokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtValidator jwtValidator;
    private final JwtGenerator jwtGenerator;
    private final TokenRepository tokenRepository;

    /**
     * 토큰 재발급 로직
     * 1.토큰 서명, 만료 검사
     * 2.토큰 사용 가능 여부 검사
     * 3.토큰 재 발급
     */
    public Token reissue(String refreshToken) throws InvalidTokenException, ExpiredJwtException, SignatureException {
        // 1.토큰 서명, 만료 검사
        String userId = jwtValidator.validateToken(refreshToken);

        // 2.토큰 사용 가능 여부 검사
        tokenRepository.existsByUserId(userId);

        String newAccessToken = jwtGenerator.generateAccessToken(Long.parseLong(userId));
        String newRefreshToken = jwtGenerator.generateRefreshToken(Long.parseLong(userId));

        return new Token(newAccessToken, newRefreshToken);
    }
}
