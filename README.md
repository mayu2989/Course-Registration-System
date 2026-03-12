[![Render](https://img.shields.io/badge/Deployed%20on-Render-46E3B7)](https://course-registration-system-2-877a.onrender.com)
# Course Registration System (Backend)

A backend REST API for a **Course Registration System** built with **Spring Boot**.
It provides secure APIs for managing users, courses, and student enrollments.

---

# Live Deployment

The backend is deployed on Render and can be accessed at:

https://course-registration-system-2-877a.onrender.com

You can use this base URL to test the APIs.

---

# Features

* User Registration
* User Login with JWT Authentication
* Role-Based Access Control (Admin / Student)
* Course Management
* Course Enrollment
* Secure REST APIs
* Global Exception Handling
* Docker Support
* Cloud Deployment (Render)

---

# Tech Stack

* Java
* Spring Boot
* Spring Security
* JWT Authentication
* PostgreSQL
* JPA / Hibernate
* Maven
* Docker

---

# Authentication

The system uses **JWT authentication**.

After login, the API returns a token that must be included in all protected requests.

Example header:

```
Authorization: Bearer <JWT_TOKEN>
```

---

# API Endpoints

## Authentication

### Register User

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

### Login

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

## Courses

### Get All Courses

GET `/courses`

---

### Create Course (Admin)

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

### Delete Course (Admin)

DELETE `/courses/{id}`

---

## Enrollment

### Enroll in Course

POST `/enroll`

Request Body

```
{
  "courseId": ""
}
```

---

### View My Courses

GET `/students/me/courses`

---

# Running Locally

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

Server will run on

```
http://localhost:8080
```

---

# Configuration

Application configuration is stored in:

```
src/main/resources/application.properties
```

This includes:

* Database configuration
* Security configuration
* Deployment configuration

---

# Docker

Build image

```
docker build -t course-registration-system .
```

Run container

```
docker run -p 8080:8080 course-registration-system
```

---

# Future Improvements

* React Frontend
* Course Search
* Pagination
* Admin Dashboard
* Email Notifications

---

# License

This project is created for educational purposes.
