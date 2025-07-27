package entus.authServer.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({InvalidTokenException.class, ExpiredJwtException.class, SignatureException.class})
    public String handleAuthExceptions(HttpServletRequest request, HttpServletResponse response) {
        //1. Security Context 초기화
        SecurityContextHolder.clearContext();

        //2. 세션 초기화
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();

        return "redirect:/login?error";
    }
}
