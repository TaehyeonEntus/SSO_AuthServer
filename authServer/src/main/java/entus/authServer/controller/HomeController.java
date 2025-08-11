package entus.authServer.controller;

import entus.authServer.domain.dto.HomeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @Value("${resource.servers}")
    private List<String> resourceServers;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @ResponseBody
    @GetMapping("/api/home")
    public HomeResponseDto apiHome() {
        return new HomeResponseDto(resourceServers);
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
