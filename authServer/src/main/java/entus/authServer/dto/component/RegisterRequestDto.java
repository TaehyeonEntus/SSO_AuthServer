package entus.authServer.dto.component;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto {
    @Size(min = 4, message = "아이디는 4자 이상 입니다.")
    private String username;
    @Size(min = 4, message = "비밀번호는 4자 이상 입니다.")
    private String password;
    @Size(min = 4, message = "비밀번호는 4자 이상 입니다.")
    private String passwordConfirm;
    @NotBlank(message = "닉네임은 필수 입니다.")
    private String name;
}
