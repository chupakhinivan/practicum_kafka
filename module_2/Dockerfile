# Используем базовый образ с Java 17
FROM openjdk:17-jdk-alpine

# Устанавливаем libstdc++
RUN apk add --no-cache libstdc++

WORKDIR /app
COPY target/module_2-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]