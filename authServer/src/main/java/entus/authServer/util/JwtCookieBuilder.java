package entus.authServer.util;

import entus.authServer.domain.user.User;
import entus.authServer.service.authorization.JwtGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtCookieBuilder {
    private final JwtGenerator jwtGenerator;
    public void createJwtCookieResponse(HttpServletRequest request,
                                         HttpServletResponse response, User user) {
        String accessToken = jwtGenerator.generateAccessToken(user);
        String refreshToken = jwtGenerator.generateRefreshToken(user);

        Cookie accessCookie = new Cookie("access_token", accessToken);
        accessCookie.setMaxAge(600);
        accessCookie.setDomain("localhost");
        accessCookie.setHttpOnly(true);
        accessCookie.setPath("/");
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
        refreshCookie.setMaxAge(604800);
        refreshCookie.setDomain("localhost");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);
    }
}