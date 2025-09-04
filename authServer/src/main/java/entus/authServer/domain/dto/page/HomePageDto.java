package entus.authServer.domain.dto.page;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HomePageDto {
    private List<String> resourceServers;
}
