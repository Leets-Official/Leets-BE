FROM openjdk:17-jdk

COPY / /workspace
WORKDIR /workspace

RUN chmod +x gradlew && ./gradlew clean build

ARG JAR_FILE=./build/libs/leets-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]