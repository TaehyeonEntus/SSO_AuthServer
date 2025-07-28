package entus.authServer.util;

import entus.authServer.domain.user.social.wrapper.GithubOAuth2UserInfo;
import entus.authServer.domain.user.social.wrapper.GoogleOAuth2UserInfo;
import entus.authServer.domain.user.social.wrapper.OAuth2UserInfo;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 사용 중인 소셜 로그인 종류를 User에 주입
 */
@Component
public class OAuth2UserInfoFactory {
    public OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase("google")) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase("github")) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationException("지원하지 않는 로그인 타입입니다: " + registrationId);
        }
    }
}
