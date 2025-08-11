package entus.authServer.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class HomeResponseDto {
    List<String> resourceServers;

    public HomeResponseDto(List<String> resourceServers) {
        this.resourceServers = resourceServers;
    }
}
