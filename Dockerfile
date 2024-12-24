# OpenJDK 17 이미지를 기본 이미지로 사용
FROM openjdk:21-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 로컬 시스템에서 JAR 파일을 Docker 컨테이너에 복사
# 예: 로컬에서 'target/myapp.jar'을 컨테이너의 /app 디렉토리로 복사
COPY build/libs/*.jar /app/myapp.jar

# 컨테이너 실행 시 JAR 파일을 실행하도록 설정
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]
