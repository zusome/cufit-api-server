# 1단계: 빌드 환경
FROM gradle:8.10.2-jdk23 AS build

WORKDIR /app
COPY . /app

# Gradle 빌드 실행
RUN gradle build --no-daemon

# 2단계: 실행 환경
FROM eclipse-temurin:23-jre-slim

# 빌드된 JAR 파일을 실행 환경으로 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 컨테이너에서 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
