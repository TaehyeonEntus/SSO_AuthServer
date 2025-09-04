package entus.authServer.controller;

import entus.authServer.domain.dto.page.HomePageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ApiController {
    @Value("${resource.servers}")
    private final List<String> resourceServers;

    @GetMapping("/home")
    public HomePageDto apiHome() {
        return new HomePageDto(resourceServers);
    }
}
