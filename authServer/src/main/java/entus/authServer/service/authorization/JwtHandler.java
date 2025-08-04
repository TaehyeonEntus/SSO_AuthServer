package entus.authServer.service.authorization;

import entus.authServer.domain.user.User;
import entus.authServer.repository.UserRepository;
import entus.authServer.util.JwtCookieBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * 로그인 시 JWT 발급 로직
 * 프레임워크 내부적 실행이라 명시적으로 json 작성
 */
@Service
@RequiredArgsConstructor
public class JwtHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtCookieBuilder jwtCookieBuilder;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String name = authentication.getName();
        User user = userRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));
        jwtCookieBuilder.createJwtCookieResponse(request, response, user);
        String redirectUrl = request.getParameter("redirectUrl");
        if(redirectUrl != null)
            response.sendRedirect(URLDecoder.decode(redirectUrl));
        else
            response.sendRedirect("http://localhost:8080/user");
    }
}
