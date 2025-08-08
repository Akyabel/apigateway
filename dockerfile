FROM maven:3.9.5-eclipse-temurin-20 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTest

FROM openjdk:20-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
