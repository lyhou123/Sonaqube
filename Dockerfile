# Builder stage
FROM gradle:8.4-jdk17-alpine AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

# Application stage
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar


EXPOSE 8080
VOLUME /home/cyber/media
VOLUME /access-refresh-token-keys
VOLUME /git_clone_repos

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "app.jar"]