# 소개

- SSO(Single Sign-On) 시스템을 위한 **인증(Authentication)과 인가(Authorization)의 책임이 분리된 서버**입니다.  
- 본 서버는 사용자 인증과 토큰 발급을 담당하며, 토큰에 대한 인증은 각각의 리소스 서버에서 수행됩니다.  
# 인증

- 로컬 유저는 ID/PW 기반 **폼 로그인(Form Login)** 을 통해 인증합니다.
- 소셜 유저(GitHub, Google 등)는 **OAuth2** 프로토콜을 통해 인증합니다.

# 인가

- 인증에 성공하면 Access Token과 Refresh Token이 발급됩니다.
- 발급된 Access Token은 Resource Server에서 검증 및 인가 처리를 수행합니다.
- Access Token이 만료되었을 경우, Refresh Token을 통해 새로운 토큰을 재발급받을 수 있습니다.

# 환경 설정 (`.env`)

`.env` 파일을 루트 디렉토리에 생성하고 다음과 같이 환경변수를 설정합니다:

```env
# GitHub OAuth2 설정
GITHUB_CLIENT_ID=your_github_client_id
GITHUB_CLIENT_SECRET=your_github_client_secret

# JWT 서명용 비밀 키
JWT_SECRET=your_jwt_secret
```
⚠️ .env 파일은 .gitignore에 포함시켜 버전 관리에서 제외하는 것을 권장합니다.

# 주의 사항

- 기본적으로 로그인 및 회원 가입 폼에 대해서 SSR(Server Side Rendering)을 지원하지만 프론트 엔드 서버 사용시 CSR(Client Side Rendering)으로 전환할 수 있습니다.
- refresh 토큰의 악용 방지 및 생명 주기 관리를 위해 Redis를 사용합니다. 토큰 서명/만료 여부만 확인하고 싶은 경우는 Redis를 제외하고 사용 가능합니다.
