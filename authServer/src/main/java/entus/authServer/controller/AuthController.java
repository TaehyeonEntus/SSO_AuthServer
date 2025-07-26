package entus.authServer.controller;

import entus.authServer.domain.Token;
import entus.authServer.domain.local.LocalUserRegisterDTO;
import entus.authServer.service.local.LocalUserService;
import entus.authServer.service.token.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    public Token refreshToken(@RequestBody String refreshToken){
        return jwtService.reissue(refreshToken);
    }
}
