package entus.authServer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/public")
    public String publicMethod() {
        return "public";
    }

    @GetMapping("/user")
    public String userMethod() {
        return "user";
    }

    @GetMapping("/admin")
    public String adminMethod() {
        return "admin";
    }
}
