FROM openjdk:17-jdk-slim

WORKDIR /app

# 빌드 시 전달 가능한 인자 정의
ARG GITHUB_CLIENT_ID
ARG GITHUB_CLIENT_SECRET
ARG JWT_SECRET
ARG REDIS_URL

# 컨테이너 실행 시 환경변수로 사용
ENV GITHUB_CLIENT_ID=${GITHUB_CLIENT_ID}
ENV GITHUB_CLIENT_SECRET=${GITHUB_CLIENT_SECRET}
ENV JWT_SECRET=${JWT_SECRET}
ENV REDIS_URL=${REDIS_URL}

# 현재 디렉토리(authServer)의 모든 파일 복사
COPY . .

# Gradle 실행 권한 및 빌드
RUN chmod +x ./gradlew
RUN ./gradlew clean build

EXPOSE 10000
CMD ["java", "-jar", "build/libs/authServer-0.0.1-SNAPSHOT.jar"]
