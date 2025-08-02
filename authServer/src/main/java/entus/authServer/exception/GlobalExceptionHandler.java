package entus.authServer.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({InvalidTokenException.class, ExpiredJwtException.class, SignatureException.class})
    public String handleAuthExceptions(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/login?error=token";
    }
}
