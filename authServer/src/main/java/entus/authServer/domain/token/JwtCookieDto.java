package entus.authServer.domain.token;

import lombok.Data;

@Data
public class JwtCookieDto {
    private String userId;
    private String accessToken;
    private String refreshToken;

    public JwtCookieDto(String userId, String accessToken, String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
