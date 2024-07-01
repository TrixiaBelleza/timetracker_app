# Use a multi-stage build to reduce the final image size
# First stage: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Second stage: Create the actual runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/timetracker-0.0.1-SNAPSHOT.jar  app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
