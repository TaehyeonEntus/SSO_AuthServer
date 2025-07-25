package entus.authServer.domain.local;

import lombok.Data;

@Data
public class LocalUserRegisterDTO {
    private String username;

    private String password;

    private String name;
}
