package entus.authServer.service.authentication.social;

import entus.authServer.domain.user.social.CustomOAuth2User;
import entus.authServer.domain.user.social.wrapper.OAuth2UserInfo;
import entus.authServer.domain.user.AuthProvider;
import entus.authServer.domain.user.Role;
import entus.authServer.domain.user.User;
import entus.authServer.repository.UserRepository;
import entus.authServer.util.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserManager extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        try {
            return processUser(userRequest, oauth2User);
        } catch (Exception ex) {
            throw new OAuth2AuthenticationException("OAuth2 처리 중 오류 발생");
        }
    }

    /**
     * 갱신 or 등록
     * 소셜 계정은 Provider, ProviderId로 구분
     */
    private OAuth2User processUser(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oauth2User.getAttributes());

        AuthProvider provider = AuthProvider.valueOf(registrationId.toUpperCase());
        String providerId = userInfo.getId();

        Optional<User> userOptional = userRepository.findByProviderAndProviderId(provider, providerId);
        User user;
        user = userOptional.map(value -> updateUser(value, userInfo)).orElseGet(() -> registerUser(userInfo, provider));

        return new CustomOAuth2User(user, oauth2User.getAttributes());
    }

    /**
     * 새로운 소셜 사용자 등록
     */
    private User registerUser(OAuth2UserInfo userInfo, AuthProvider provider) {
        User user = new User();

        user.setUsername(null);
        user.setPassword(null);
        user.setProvider(provider);
        user.setProviderId(userInfo.getId());
        user.setName(userInfo.getName());
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    /**
     * 기존 소셜 사용자 최신화
     */
    private User updateUser(User existingUser, OAuth2UserInfo userInfo) {
        existingUser.setName(userInfo.getName());
        return userRepository.save(existingUser);
    }
}
