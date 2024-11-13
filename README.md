# Task List API

### Overview

The Task List API is a Java Spring Boot application designed to manage tasks of different types. It supports operations for creating, updating, viewing, and deleting tasks. The application includes support for three different task types: **Standard Tasks**, **Timed Tasks**, and **Due Date Tasks**.

Each task type has specific attributes and functionalities, which makes the application flexible for managing various types of to-do items. The API is built using Java Spring Boot and uses H2 as the in-memory database for persistence.

### Features

- Create, update, view, and delete tasks.
- Supports **Standard Tasks**, **Timed Tasks** (with a start and end time), and **Due Date Tasks** (with a specific due date).
- Provides a structured JSON response with error handling for better integration.
- Built-in validation for input data to ensure the correct format and completeness of requests.
- Simple structure to enable easy extension and customization.

### Requirements

- Java 21 or higher
- Maven 3.6 or higher
- Spring Boot 2.5+

### Dependencies

The project uses the following dependencies:
- **Spring Web**: For creating REST APIs.
- **Spring Data JPA**: For database interactions.
- **H2 Database**: An in-memory database used for testing and development purposes.
- **Jakarta Validation**: For request validation.
- **Lombok**: To reduce boilerplate code.
- **Jackson Datatype JSR310**: For handling Java 8 Date and Time types.

### Setup and Running the Application

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd tasklist-api
   ```

2. **Install dependencies** using Maven:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the H2 database console** (optional, for debugging purposes):
    - Open your browser and navigate to: `http://localhost:8080/h2-console`
    - Set JDBC URL to `jdbc:h2:mem:testdb`.

### API Routes

The following endpoints are available in the Task List API:

1. **Get All Tasks**
    - **URL**: `/api/tasks`
    - **Method**: `GET`
    - **Description**: Retrieves all tasks grouped by their type (Standard, Timed, Due Date).
    - **Curl Command**:
      ```bash
      curl -X GET http://localhost:8080/api/tasks
      ```

2. **Get Task by ID**
    - **URL**: `/api/tasks/{id}`
    - **Method**: `GET`
    - **Description**: Retrieves a specific task by its ID.
    - **Curl Command**:
      ```bash
      curl -X GET http://localhost:8080/api/tasks/1
      ```

3. **Create Task**
    - **URL**: `/api/tasks/create`
    - **Method**: `POST`
    - **Description**: Creates a new task. The request body should include a `task_type` field (one of `standardTasks`, `dueDateTasks`, `timedTasks`) and other task-specific attributes.
    - **Curl Command**:
      ```bash
      curl -X POST http://localhost:8080/api/tasks/create -H "Content-Type: application/json" -d '{
        "task_type": "timedTasks",
        "title": "Study for exam",
        "description": "Focus on math section",
        "startTime": "2024-11-13T09:00:00",
        "endTime": "2024-11-13T11:00:00"
      }'
      ```

4. **Update Task**
    - **URL**: `/api/tasks/{id}`
    - **Method**: `PUT`
    - **Description**: Updates an existing task by its ID. The request body should contain updated fields based on the type of task.
    - **Curl Command**:
      ```bash
      curl -X PUT http://localhost:8080/api/tasks/1 -H "Content-Type: application/json" -d '{
        "title": "Updated title",
        "description": "Updated description"
      }'
      ```

5. **Delete Task**
    - **URL**: `/api/tasks/{id}`
    - **Method**: `DELETE`
    - **Description**: Deletes a task by its ID.
    - **Curl Command**:
      ```bash
      curl -X DELETE http://localhost:8080/api/tasks/1
      ```

### Example Request

To **create a new timed task**:

```json
{
  "task_type": "timedTasks",
  "title": "Study for exam",
  "description": "Focus on math section",
  "startTime": "2024-11-13T09:00:00",
  "endTime": "2024-11-13T11:00:00"
}
```

### Error Handling

The API includes comprehensive error handling to provide meaningful messages in response to invalid requests. For example, if a task with a specific ID is not found, a `404 Not Found` response is returned with a message like:

```json
{
  "status": "error",
  "message": "Task with ID 5 not found"
}
```

### Future Improvements

- **Authentication and Authorization**: Add user roles to ensure tasks are managed securely.
- **Testing**: Implement unit and integration tests for better coverage.
- **Pagination**: Add pagination for retrieving tasks to handle large datasets more efficiently.

Feel free to contribute or suggest improvements by submitting a pull request or an issue in the repository.

### Environment Variables

To set the CORS, you can use an environment variable. In `application.properties`, you can reference the environment variable like this:

```properties
app.cors.allowed-origins=${CORS_ALLOWED_ORIGINS:http://localhost:5173}
```


