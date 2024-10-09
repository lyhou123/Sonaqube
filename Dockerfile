# Builder stage
FROM gradle:8.4-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

# Application stage
FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8080
VOLUME /home/cyber/media
VOLUME /keys

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=pro", "app.jar"]