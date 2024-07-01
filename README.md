# TimeTracker Application

## Introduction

This is a TimeTracker application developed using Java, Spring Boot, and MySQL. The application allows users to register, log in, and track their time entries for different projects.

## Features

- User registration and login
- Adding time entries with hours, task description, and project selection
- Viewing weekly work and project totals

## Prerequisites

- Docker
- Docker Compose
- Git

## Cloning the Repository

To clone the repository, use the following command:

```sh
git clone https://github.com/TrixiaBelleza/timetracker_app.git
cd timetracker-app
```

## Building and Running the Application

1. Ensure you have Docker installed on your machine
2. Build the Docker images and start the containers.
```sh
docker-compose build
docker-compose up
```

3. The application will be available at `http://localhost:8080`

Note: In case you may run into docker issues after running the `docker-compose up`. Check your Docker application and manually click the run button for the timetracker server.

## Running the tests
To run the tests, use the following command
```sh
./mvnw test
```

## Environment Variables
The following environment variables are used in the application:

 - SPRING_DATASOURCE_URL: The URL of the MySQL database.
 - SPRING_DATASOURCE_USERNAME: The username for the MySQL database.
 - SPRING_DATASOURCE_PASSWORD: The password for the MySQL database.
 - SPRING_JPA_HIBERNATE_DDL_AUTO: Hibernate DDL auto setting (e.g., update).
 - SPRING_JPA_SHOW_SQL: Show SQL queries in the logs.

## Building the Application Manually
If you prefer to build and run the app manually, follow these steps:

1. Ensure you have JDK 17 and Maven installed. You may use SDKMAN to switch java versions on a specific terminal session.
```sh
sdk use java 17.0.1-open
```

2. Build the app using Maven
```sh
mvn clean install
```

3. Run the application
```sh
cd target
java -jar target/timetracker-0.0.1-SNAPSHOT.jar
```

The application will be available at `http://localhost:8080`
