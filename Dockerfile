# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml first to cache dependencies
COPY pom.xml .
RUN mvn -e -B dependency:go-offline

# Now copy the actual source code
COPY src ./src

# Build the project (this generates the jar in /app/target)
RUN mvn -e -B clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
