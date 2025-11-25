FROM eclipse-temurin:17-jre

ARG JAR_FILE=./build/libs/leets-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]
