package entus.authServer.controller;

import entus.authServer.dto.page.HomePageDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ApiController {
    @Value("${RESOURCE_SERVERS}")
    private String resourceServers;
    private List<String> resourceServersList;

    @PostConstruct
    public void init() {
        resourceServersList = Arrays.stream(resourceServers.split(",")).toList();
    }

    @GetMapping("/home")
    public HomePageDto apiHome() {
        return new HomePageDto(resourceServersList);
    }
}
