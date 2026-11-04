# Stage 1: Build stage using Maven and Java 17 JDK
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies for offline caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the packaged Spring Boot executable JAR
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Runtime stage using lightweight Java 17 JRE
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Create a non-root system user for security best practices
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copy the built JAR artifact from the build stage
COPY --from=build /app/target/menteencasa-bc3-1.0.0.jar app.jar

# Expose application port
EXPOSE 8080

# Execute the application
ENTRYPOINT ["java", "-jar", "app.jar"]
