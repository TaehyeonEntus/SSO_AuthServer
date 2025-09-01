package entus.authServer.service.authentication.local;

import entus.authServer.domain.user.local.CustomUserDetails;
import entus.authServer.domain.user.User;
import entus.authServer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Security 내부 에서 User 찾을 때 사용할 구현체
 * LocalUser는 User DB에서 조회함
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsManager implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new CustomUserDetails(user);
    }
}