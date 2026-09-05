# Étape 1 : Build de l'application (passage à Temurin 21)
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

# Étape 2 : Runtime applicatif (passage à Temurin 21)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN mkdir -p /app/storage/documents
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]