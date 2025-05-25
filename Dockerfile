# Etapa 1: build con Maven
FROM maven:3.9.6-eclipse-temurin-17-alpine as builder

WORKDIR /app

# Copiar pom.xml y dependencias primero
COPY pom.xml .
COPY src ./src

# Compilar sin tests
RUN mvn clean package -DskipTests

# Etapa 2: imagen ligera
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiar el .jar desde el builder
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
