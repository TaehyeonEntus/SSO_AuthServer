package entus.authServer.domain.dto.component;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDto {
    @Size(min = 4, message = "아이디는 4자 이상 입니다.")
    private String username;
    @Size(min = 4, message = "비밀번호는 4자 이상 입니다.")
    private String password;
}
