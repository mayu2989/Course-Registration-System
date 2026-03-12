# Course Registration System (Backend)

This project is a **Spring Boot backend application** for a Course Registration System.
It provides REST APIs for managing users, courses, and student enrollments.

The system supports **secure authentication using JWT** and **role-based access control** for administrators and students.

---

## Features

* User registration and login
* JWT-based authentication
* Role-based authorization (Admin / Student)
* Course management
* Student course enrollment
* RESTful API design
* Global exception handling
* Input validation
* PostgreSQL database integration
* Docker support for containerized deployment

---

## Tech Stack

* Java
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* JPA / Hibernate
* PostgreSQL
* Maven
* Docker

---

## Project Structure

```
src/main/java
├── controller
├── service
├── repository
├── model
├── dto
├── security
├── exception
└── config
```

---

## Authentication

The system uses **JWT authentication**.

After a successful login, the API returns a token that must be included in all protected requests.

Example header:

```
Authorization: Bearer <JWT_TOKEN>
```

---

## API Endpoints

### Authentication

**Register User**

POST `/auth/register`

Request Body

```
{
  "username": "",
  "email": "",
  "password": ""
}
```

---

**Login**

POST `/auth/login`

Request Body

```
{
  "username": "",
  "password": ""
}
```

Response

```
{
  "token": ""
}
```

---

### Courses

**Get All Courses**

GET `/courses`

---

**Create Course (Admin)**

POST `/courses`

Request Body

```
{
  "name": "",
  "description": "",
  "instructor": ""
}
```

---

**Delete Course (Admin)**

DELETE `/courses/{id}`

---

### Enrollment

**Enroll in Course**

POST `/enroll`

Request Body

```
{
  "courseId": ""
}
```

---

**View My Courses**

GET `/students/me/courses`

---

## Running the Project Locally

Clone the repository

```
git clone <repository-url>
cd course-registration-system
```

Build the project

```
mvn clean install
```

Run the application

```
mvn spring-boot:run
```

The application will start at:

```
http://localhost:8080
```

---

## Configuration

Application settings are configured in:

```
src/main/resources/application.properties
```

This includes:

* Database configuration
* Security configuration
* Environment-specific properties

---

## Docker Support

Build the Docker image:

```
docker build -t course-registration-system .
```

Run the container:

```
docker run -p 8080:8080 course-registration-system
```

---

## Deployment

The backend can be deployed on cloud platforms such as:

* Render
* AWS
* Docker containers

---

## Future Improvements

* React frontend for user interface
* Course search and filtering
* Pagination for course listings
* Admin dashboard
* Email notifications

---

## License

This project is intended for educational and learning purposes.
