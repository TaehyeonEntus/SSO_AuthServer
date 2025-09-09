package entus.authServer.controller;

import entus.authServer.dto.component.LoginRequestDto;
import entus.authServer.dto.component.RegisterRequestDto;
import entus.authServer.domain.user.local.dto.LocalUserRegisterDTO;
import entus.authServer.service.authentication.local.LocalUserService;
import entus.authServer.service.authorization.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final LocalUserService localUserService;
    private final JwtService jwtService;

    @PostMapping("/login/form-valid")
    public ResponseEntity<?> loginFormValid(@Valid @RequestBody LoginRequestDto dto, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        //valid 검사
        System.out.println(dto.getUsername());
        System.out.println(dto.getPassword());
        System.out.println(bindingResult.getAllErrors());
        if (bindingResult.hasErrors()) {
            bindingResult
                    .getFieldErrors()
                    .forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto dto, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        //1차 valid 검사
        if (bindingResult.hasErrors()) {
            bindingResult
                    .getFieldErrors()
                    .forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        //비밀번호 일치 검사
        if(!dto.getPassword().equals(dto.getPasswordConfirm())){
            errors.put("passwordConfirm", "비밀번호가 일치하지 않습니다.");
            return ResponseEntity.badRequest().body(errors);
        }

        //가입 가능 여부 검사
        try {
            localUserService.registerUser(new LocalUserRegisterDTO(dto));
        } catch (IllegalArgumentException e) {
            errors.put("username", "이미 사용중인 아이디 입니다.");
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies)
            if (cookie != null)
                if (cookie.getName().equals("refresh_token"))
                    refreshToken = cookie.getValue();

        if (refreshToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("refresh token null");

        jwtService.reissue(request, response, refreshToken);
        return ResponseEntity.ok().build();
    }
}
