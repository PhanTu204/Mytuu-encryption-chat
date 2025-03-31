# Mytuu-encryption-chat-
# Secure Messaging Web App

## Project Overview
This project is a secure messaging web application built with **Spring Boot** as the backend. It features user authentication, registration, JWT-based security, and user information management. The goal is to provide a secure and seamless communication platform.

## Technologies Used
- **Java 23** (Spring Boot for backend development)
- **Spring Security** (JWT-based authentication & authorization)
- **Spring Data JPA** (User management & database interactions)
- **JUnit & Mockito** (Unit testing)
- **Postman** (API testing)
- **Figma** (UI/UX design for the messaging app)

## Features Implemented
### 1. User Authentication
- Implemented user registration and login.
- Supported authentication via username, email, or phone number.
- Used **BCrypt** to hash passwords.
- JWT-based authentication for securing API endpoints.

### 2. JWT Security
- Created **JwtUtil** to generate and validate **Access Tokens** & **Refresh Tokens**.
- Developed **JwtFilter** to verify JWT in requests and set authentication context.
- Configured **Spring Security** to secure API endpoints and allow only authenticated users.

### 3. User Registration & Profile Update
- Designed `User` entity with fields: `Full Name`, `Email`, `Phone Number`, `Date of Birth`, and `Gender`.
- Implemented **DTOs** for structured data transfer.
- Separated **UpdateUserService** to handle user profile updates.

### 4. API Testing with Postman
- Set up **Postman** collections to test API endpoints.
- Performed authentication tests for login and registration.
- Verified JWT-based security in Postman.

### 5. Unit Testing
- Implemented **JUnit 5** and **Mockito** tests for `LoginService`.
- Created mock objects for repository and password encoder.
- Covered cases for valid login, incorrect credentials, and non-existent users.


