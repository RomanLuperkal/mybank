FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY build/libs/*.jar account-service.jar

ENTRYPOINT ["java", "-jar", "account-service.jar"]
