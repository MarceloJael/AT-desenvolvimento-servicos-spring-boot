# Etapa de build
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de execução
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/at-0.0.1-SNAPSHOT.jar /app/at.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "at.jar"]
