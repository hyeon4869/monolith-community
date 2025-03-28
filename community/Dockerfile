# Base image 설정
FROM openjdk:17-jdk-slim

# JAR 파일 복사
ARG JAR_FILE=target/community-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 컨테이너에서 실행할 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]
