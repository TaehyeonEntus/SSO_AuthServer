package entus.authServer.util;

import entus.authServer.domain.user.User;
import entus.authServer.service.authorization.JwtGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


//쿠키에 access, refresh 토큰 넣어주는 빌더
@Component
@RequiredArgsConstructor
public class JwtCookieBuilder {
    private final JwtGenerator jwtGenerator;

    @Value("${BASIC_DOMAIN}")
    private String basicDomain;

    @Value("${ACCESS_TOKEN_AGE}")
    private int accessTokenAge;

    @Value("${REFRESH_TOKEN_AGE}")
    private int refreshTokenAge;

    public void createJwtCookieResponse(HttpServletRequest request,
                                         HttpServletResponse response, User user) {
        String accessToken = jwtGenerator.generateAccessToken(user);
        String refreshToken = jwtGenerator.generateRefreshToken(user);

        Cookie accessCookie = new Cookie("access_token", accessToken);
        accessCookie.setMaxAge(accessTokenAge);
        accessCookie.setDomain(basicDomain);
        accessCookie.setHttpOnly(true);
        accessCookie.setPath("/");
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
        refreshCookie.setMaxAge(refreshTokenAge);
        refreshCookie.setDomain(basicDomain);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);
    }

    public void deleteJwtCookieResponse(HttpServletRequest request,
                                        HttpServletResponse response) {
        Cookie accessCookie = new Cookie("access_token", null);
        accessCookie.setMaxAge(0);
        accessCookie.setDomain(basicDomain);
        accessCookie.setHttpOnly(true);
        accessCookie.setPath("/");
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refresh_token", null);
        refreshCookie.setMaxAge(0);
        refreshCookie.setDomain(basicDomain);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);
    }
}