package entus.authServer.service.authorization;

import entus.authServer.domain.token.Token;
import entus.authServer.domain.user.User;
import entus.authServer.exception.InvalidTokenException;
import entus.authServer.repository.TokenRepository;
import entus.authServer.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtValidator jwtValidator;
    private final JwtGenerator jwtGenerator;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

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
        if(!tokenRepository.existsByUserId(userId))
            throw new InvalidTokenException();

        // 2.5 클레임에 넣을 사용자 세부 정보
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));

        // 3.토큰 재 발급
        String newAccessToken = jwtGenerator.generateAccessToken(user);
        String newRefreshToken = jwtGenerator.generateRefreshToken(user);

        return new Token(newAccessToken, newRefreshToken);
    }
}
