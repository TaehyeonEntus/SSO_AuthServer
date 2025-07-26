package entus.authServer.domain.user.local.dto;

import lombok.Data;

@Data
public class LocalUserRegisterDTO {
    private String username;

    private String password;

    private String name;
}
