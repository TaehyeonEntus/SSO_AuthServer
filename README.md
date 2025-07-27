# 인증
로컬 유저는 ID/PW 기반 폼 로그인을 통해 인증

소셜 유저(Git, Google, etc)는 OAuth2를 통해 인증

# 인가
본 서버는 사용자 인증, 토큰 발급을 수행하는 서버입니다.

토큰 인증은 리소스 서버에서 수행하되, 만료시 Refresh Token으로 재 발급받을 수 있습니다.

# 환경 설정

```env
GITHUB_CLIENT_ID = your_github_client_key
GITHUB_CLIENT_SECRET = your_github_client_secret

JWT_SECRET = your_jwt_secret
```
