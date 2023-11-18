FROM openjdk:17-jdk

ARG JAR_FILE=./build/libs/leets-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]