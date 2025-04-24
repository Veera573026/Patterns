# Stage 1: Build the application
FROM maven:3-eclipse-temurin-17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-alpine

# Set working directory in runtime container
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/DogManagementSystem-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
