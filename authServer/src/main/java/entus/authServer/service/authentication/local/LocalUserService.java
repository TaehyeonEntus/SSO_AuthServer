package entus.authServer.service.authentication.local;

import entus.authServer.domain.user.AuthProvider;
import entus.authServer.domain.user.Role;
import entus.authServer.domain.user.User;
import entus.authServer.domain.user.local.dto.LocalUserRegisterDTO;
import entus.authServer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 신규 유저 등록
     */
    public User registerUser(LocalUserRegisterDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);
        user.setName(dto.getName());
        user.setProvider(AuthProvider.LOCAL); // 로컬 로그인 사용자
        user.setProviderId(null); // 소셜 아님

        return userRepository.save(user);
    }
}
