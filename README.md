
# Game Service Provider Application

This is a Java application built using Spring Boot, Maven, and an in-memory cache to manage games in a game service provider environment.

## Features

- Create a new game with a unique name, creation date, and active status.
- Retrieve information about a specific game by name.
- Get a list of all games.
- Update an existing game with new information.
- Delete a game by name.

## Technologies Used

- Java
- Spring Boot
- Maven
- Junit 5

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or above
- Maven

### Running the Application

1. Clone the repository:

   ```shell
   git clone <repository_url>
   ```

2. Change to the project directory:

   ```shell
   cd GameApp
   ```

3. Build the application using Maven:

   ```shell
   mvn clean package
   ```

4. Run the application:

   ```shell
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`.

## API Documentation

The application exposes REST endpoints for managing games. You can use tools like Postman or cURL to interact with the API.

### Create a Game

- **URL**: `/games`
- **Method**: `POST`
- **Request Body**:

  ```json
  {
    "name": "Jitunu Sahoo",
    "creationDate": "08/07/2023 12:23:30",
    "active": true
  }
  ```

- **Response Body**:

  ```json
  {
    "name": "Jitunu Sahoo",
    "creationDate": "08/07/2023 12:23:30",
    "active": true
  }
  ```
- **Response Http Status**: 201

### Get a Game

- **URL**: `/games/{name}`
- **Method**: `GET`
- **Response Body**:

  ```json
  {
    "name": "Jitunu Sahoo",
    "creationDate": "08/07/2023 12:23:30",
    "active": true
  }
  ```
- **Response Http Status**: 200

### Get All Games

- **URL**: `/games`
- **Method**: `GET`
- **Response Body**:

  ```json
  [
    {
      "name": "Jitunu",
      "creationDate": "08/07/2023 12:23:30",
      "active": true
    },
    {
      "name": "Santanu",
      "creationDate": "08/07/2023 12:23:30",
      "active": false
    }
  ]
  ```
- **Response Http Status**: 200

### Update a Game

- **URL**: `/games/{name}`
- **Method**: `PUT`
- **Request Body**:

  ```json
  {
    "name": "Updated Game Name",
    "creationDate": "08/07/2023 12:23:30",
    "active": false
  }
  ```

- **Response Body**:

  ```json
  {
    "name": "Updated Game Name",
    "creationDate": "08/07/2023 12:23:30",
    "active": false
  }
  ```
- **Response Http Status**: 200

### Delete a Game

- **URL**: `/games/{name}`
- **Method**: `DELETE`
- **Response Http Status**: 204

## Testing

The project includes JUnit test cases to ensure the correctness of the application. You can run the tests using the following command:

```shell
mvn test
```

## Logging

The application logs information using the AOP aspect provided by Spring Boot. You can find the logs in the console output and in logs directory.

## Authors

- [Jitunu Sahoo]


Feel free to customize the README file to match your project's specific details and requirements.