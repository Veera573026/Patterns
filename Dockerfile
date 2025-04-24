FROM maven:3-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests
FROM eclipse-temurin:17-alpine
COPY --from=build /target/DogManagementSystem-0.0.1-SNAPSHOT.jar /DogManagementSystem.jar
EXPOSE 8080
CMD ["java", "-jar", "DogManagementSystem.jar"]