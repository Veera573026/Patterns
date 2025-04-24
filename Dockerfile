# Stage 1: Build
FROM maven:3-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml first to leverage caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Now copy the source code
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-alpine
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/DogManagementSystem-0.0.1-SNAPSHOT.jar.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
