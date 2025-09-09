package entus.authServer.domain.user.local.dto;

import entus.authServer.dto.component.RegisterRequestDto;
import lombok.Data;

@Data
public class LocalUserRegisterDTO {
    private String username;

    private String password;

    private String name;

    public LocalUserRegisterDTO(RegisterRequestDto dto) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.name = dto.getName();
    }
}
