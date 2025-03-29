FROM openjdk:8

WORKDIR /app
COPY target/analytics-1.0-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]