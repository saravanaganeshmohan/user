# Project Name 

User

## Description

The project aims to provide CRUD (Create, Read, Update, Delete) operations for managing User Document, Post and Comment. It is a Java Spring Boot application with Gradle as the build tool. The application utilizes h2 as the database for storage. 

The project includes both unit testing and integration testing. TestRestTemplate is used for integration testing, allowing us to simulate and verify API interactions. OpenFeign is utilized for seamless communication with other services.

## Features

- Perform User Document CRUD operations
- Store file data in a h2 database
- Perform Post, Comment API's wiil be integrated with jsonplaceholder
- Integration testing using TestRestTemplate to ensure API functionality
- Seamless communication with external services using OpenFeign
- The files will be encrypted and decrypted using ASE algorithm.
- Used password production with user credentials in file security, without a password the file could not view.

## Installation

To install and run the project locally, please follow these steps:

1. Clone the repository to your local machine.
2. Set up a PostgreSQL database and ensure the credentials are correctly configured.
3. Update the database connection details in the project's configuration files.
4. Build the project using Gradle: ./gradlew build
5. Run the application: ./gradlew bootRun
6. Access the application via http://localhost:9080 on your preferred web browser.

## Usage

The project provides a RESTful API for performing CRUD operations on multipart files. You can interact with the API using your preferred HTTP client or tools like Postman.

## Contributing

We welcome contributions to enhance the project. To contribute, please follow these steps:

1. Fork the repository .
2. Make your changes and ensure they adhere to the project's coding standards.
3. Write tests to cover your changes, ensuring the existing tests pass.


