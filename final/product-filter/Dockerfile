# Используем базовый образ с Java 17
FROM openjdk:17-jdk-alpine

# Устанавливаем libstdc++
RUN apk add --no-cache libstdc++

WORKDIR /app
COPY target/product-filter-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]