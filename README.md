# File management application

## Introduction

This repository contains a spring boot based demo project for managing files in various file storages.

The application provides a unified interface to manage files in the host storage and AWS S3. Storage connectors can be easily extended, so the app might communicate with other storages as well.

The project also shows how to create a multi-module Maven project, manage dependencies and plugins, create REST controllers, create business logic layers using SOLID principles and behaviour patterns, connect to AWS S3 and system local file storages and manage files there, connect to a database, manage database entities, create liquibase scripts, do bean validation and proper error management, create a docker file, create docker compose file, create integration tests using test containers, create OpenAPI docs.

Used technologies: Java 17, Maven, Spring Boot, Spring MVC, Spring Data, AWS S3, PostgresDB, Liquibase, Swagger, Spring Tests, TestContainers, Docker.

*Note: The application is **only** for demonstration purposes. It's recommended to not use it in production environments.*

## Build

To build File management application, run the following command. <br>
*Note: we use the `install` goal to install the artifacts into the local maven repository in order to be able to consume the artifacts.*

`mvn clean install`

*Note: File management application tests require docker test containers. If you don't have a docker installed, run the following command instead.*

`mvn clean install -DskipTests`

## Run

**Run application without any additional configurations**<br>
To run file management application without any additional configurations, navigate to **file-management-service** folder and run the following command: <br>
`mvn spring-boot:run -Dspring-boot.run.profiles=demo,local-storage`

The application will start on port `8080`, only with local storage connector and embedded in memory H2 database. The swagger UI will be available by the link: http://localhost:8080/file-management-service/swagger-ui/index.html <br>

Uploaded files will be stored in `${user.home}` - running host user home directory.

**Run application in a docker container**<br>
To run the application in a docker container run the following command: <br>
`docker compose -f file-management-compose.yml up` <br>

The application will start on port `8080`, with a local storage connector and Postgres database. The swagger UI will be available by the link: http://localhost:8080/file-management-service/swagger-ui/index.html <br>

To run the application with S3 storage connector as well, uncomment commented lines in `file-management-compose.yml` and provide properties values.<br>
*Note: injection of AWS credentials through environment variables is not recommended and must not be used in production environments. Consider AWS IAM roles instead.*


