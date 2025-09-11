FROM openjdk:17-jdk-slim

WORKDIR /app

# 현재 디렉토리(authServer)의 모든 파일 복사
COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew clean build

EXPOSE 8080
CMD ["java", "-jar", "build/libs/authServer-0.0.1-SNAPSHOT.jar"]