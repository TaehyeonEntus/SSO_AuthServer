package entus.authServer.domain.user.social.wrapper;

import lombok.Getter;

/**
 * 각 인증 서버마다 API 문서 확인해서 이름 맵핑 해 줘야함
 */
import java.util.Map;
@Getter
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();
    public abstract String getName();
}