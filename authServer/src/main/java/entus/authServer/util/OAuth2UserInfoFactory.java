package entus.authServer.util;

import entus.authServer.domain.social.GithubOAuth2UserInfo;
import entus.authServer.domain.social.GoogleOAuth2UserInfo;
import entus.authServer.domain.social.OAuth2UserInfo;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase("google")) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase("github")) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationException("지원하지 않는 로그인 타입입니다: " + registrationId);
        }
    }
}
