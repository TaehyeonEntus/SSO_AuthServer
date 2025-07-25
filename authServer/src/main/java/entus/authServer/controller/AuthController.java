package entus.authServer.controller;

import entus.authServer.domain.local.LocalUserRegisterDTO;
import entus.authServer.service.local.LocalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final LocalUserService localUserService;
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute LocalUserRegisterDTO dto) {
        localUserService.registerUser(dto);
        return "redirect:/login";
    }
}
