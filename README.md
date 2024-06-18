# Reactive Demo Application

## Description

This reactive Spring WebFlux application manages student data using a reactive approach. It leverages Spring WebFlux for building RESTful APIs and Reactor for asynchronous, non-blocking data processing.

## Key Features

* **Reactive Data Access:** Employs a reactive repository (likely implemented using Spring Data R2dbc) for efficient, non-blocking interaction with a database (e.g., PostgreSQL, MongoDB) to store student information.
* **Event-Driven Architecture:** Utilizes Spring's ApplicationEventPublisher to publish a `StudentCreatedEvent` whenever a new student is saved. This allows for decoupled processing or notifications upon student creation.
* **WebFlux REST API:** Provides a RESTful API for CRUD (Create, Read, Update, Delete) operations on student data:
    * `GET /students/all`: Retrieves a stream of all students using Server-Sent Events (SSE) for real-time updates.
    * `POST /students/save`: Creates a new student by receiving a `StudentRequest` object in the request body.
    * `PUT /students/update/{id}`: Updates a specific student by receiving a `StudentRequest` object in the request body and student ID in a Path Variable.
    * `DELETE /students/reset`: Deletes all students.
    * `DELETE /students/delete/{id}`: Deletes a specific student by receiving student ID in a Path Variable.
* **Sinks for Backpressure Handling:** Employs a `Sinks.Many` multicaster to buffer student data emitted from the service in case of backpressure situations, ensuring data integrity and preventing data loss.

## Prerequisites

* Java 21
* Maven or Gradle (for building the project)
* A database (ideally a fully reactive database like PostgreSQL or MongoDB)

Note: If using MySQL, be aware of its limitations in terms of full reactivity.

## Setup

1. Clone this repository.
2. Configure your database connection details in the application properties file (e.g., `application.properties` or `application.yml`).
3. Build the project using Maven or Gradle (e.g., `mvn clean install` or `gradle build`).
4. Run the application using your preferred Java launcher (e.g., `java -jar target/student-reactive-app.jar`).

## Usage

1. **Retrieving All Students (SSE):**
    - Use a client that supports SSE (e.g., curl, Postman) to send a GET request to `http://localhost:8099/students/all`.
    - The response will be a continuous stream of student data in JSON format.

2. **Creating a New Student:**
    - Send a POST request to `http://localhost:8099/students/save` with the student details in JSON format in the request body.
    - The response will be the newly created student object in JSON format.

3. **Updating a Student:**
    - Send a PUT request to `http://localhost:8099/students/update/{id}` with the updated student details in JSON format in the request body and the student ID in the path.
    - The response will be the updated student object in JSON format.

4. **Deleting All Students:**
    - Send a DELETE request to `http://localhost:8099/students/reset`.
    - The response will be an acknowledgment of the deletion.

5. **Deleting a Specific Student:**
    - Send a DELETE request to `http://localhost:8099/students/delete/{id}` with the student ID in the path.
    - The response will be an acknowledgment of the deletion.