package entus.authServer.controller;

import entus.authServer.domain.user.local.dto.LocalUserRegisterDTO;
import entus.authServer.service.authentication.local.LocalUserService;
import entus.authServer.service.authorization.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final LocalUserService localUserService;
    private final JwtService jwtService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute LocalUserRegisterDTO dto) {
        localUserService.registerUser(dto);
        return "redirect:/login";
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies)
            if (cookie != null)
                if (cookie.getName().equals("refresh_token"))
                    refreshToken = cookie.getValue();

        if(refreshToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("refresh token null");

        jwtService.reissue(request, response, refreshToken);
        return ResponseEntity.ok().build();
    }
}
