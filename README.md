# 🚀 Masjedi: Mosque Management System

A comprehensive backend solution for managing mosque activities, including student enrollment, course tracking, teacher assignments, and progress monitoring.

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## 💡 Overview
Masjedi is a robust backend application designed to streamline the administration and management of mosque educational and community activities. It provides a secure and efficient way to handle student data, organize courses and lessons, assign teachers to circles, track student progress, and manage daily logbooks and notes. Built with modern Spring Boot technologies, it offers a scalable and maintainable foundation for mosque operations.

## ✨ Features
- **User and Role Management**: Secure authentication and authorization with Keycloak integration for different user roles (e.g., Admin, Teacher, Student).
- **Mosque and Circle Management**: Create and manage multiple mosques and organize students into learning circles.
- **Course and Lesson Tracking**: Define courses, add lessons, and track their order and content.
- **Student Progress Monitoring**: Record and monitor student progress through courses and lessons, including daily logbooks.
- **Teacher Assignment**: Assign teachers to specific circles and manage their student interactions.
- **Notes System**: Teachers can add and manage notes for students.
- **Comprehensive API**: A well-documented RESTful API for seamless integration with frontend applications.

## 🛠️ Technology Stack
- **Backend**: Java 21, Spring Boot 3.x
- **Database**: MySQL
- **ORM**: Spring Data JPA, Hibernate
- **Security**: Spring Security, OAuth2 Resource Server, Keycloak
- **API Documentation**: Springdoc OpenAPI (Swagger UI)
- **Build Tool**: Maven
- **Mapping**: MapStruct
- **Utility**: Lombok

## 📦 Installation

To get a local copy up and running, follow these simple steps.

### Prerequisites
- Java Development Kit (JDK) 21
- Maven 3.x
- MySQL Server
- Keycloak (for authentication, or configure for local testing without it)

### Setup Steps

1.  **Clone the repository**:
    ```bash
    git clone [repository_url]
    cd masjedi
    ```

2.  **Configure Database**:
    - Create a MySQL database named `masjedi_db`.
    - Update `src/main/resources/application.properties` with your database credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/masjedi_db?useSSL=false&serverTimezone=UTC
    spring.datasource.username=[your_mysql_username]
    spring.datasource.password=[your_mysql_password]
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ```

3.  **Configure Keycloak (Optional, for full security features)**:
    - Set up a Keycloak realm and client.
    - Update `src/main/resources/application.properties` with your Keycloak configuration:
    ```properties
    spring.security.oauth2.resourceserver.jwt.issuer-uri=[your_keycloak_issuer_uri]
    spring.security.oauth2.resourceserver.jwt.jwk-set-uri=[your_keycloak_jwk_set_uri]
    ```
    For local development without Keycloak, you might need to adjust `SecurityConfig.java` or use a simpler authentication mechanism.

4.  **Build the project**:
    ```bash
    mvn clean install
    ```

## 🚀 Usage

To run the application:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080` by default.

## 📖 API Documentation

The API documentation is available via Swagger UI. Once the application is running, navigate to:
`http://localhost:8080/swagger-ui.html`

### Key Endpoints (Example)

| Endpoint                               | Method | Description                                     | Authentication |
| :------------------------------------- | :----- | :---------------------------------------------- | :------------- |
| `/api/mosques`                         | `POST` | Create a new mosque                             | Authenticated  |
| `/api/mosques/{id}`                    | `GET`  | Get mosque details by ID                        | Authenticated  |
| `/api/circles`                         | `POST` | Create a new circle                             | Authenticated  |
| `/api/circles/{id}/assign-teacher`     | `PUT`  | Assign a teacher to a circle                    | Authenticated  |
| `/api/users/current`                   | `GET`  | Get details of the currently authenticated user | Authenticated  |
| `/api/students/{courseId}/progress`    | `GET`  | Get student progress for a specific course      | Authenticated  |
| `/api/teachers/{circleId}/students`    | `GET`  | Get students in a teacher's circle              | Authenticated  |

## ✅ Testing

To run the unit and integration tests:

```bash
mvn test
```

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1.  Fork the repository.
2.  Create a new branch (`git checkout -b feature/your-feature-name`).
3.  Make your changes.
4.  Commit your changes (`git commit -m 'Add new feature'`).
5.  Push to the branch (`git push origin feature/your-feature-name`).
6.  Open a Pull Request.

## 📄 License

This project is licensed under the Apache 2.0 License - see the [LICENSE.md](LICENSE.md) file for details. (Placeholder: Create a LICENSE.md file if not present)

## 🙏 Acknowledgments

-   Spring Boot Team
-   Keycloak Community
-   MapStruct Developers
-   Swagger/OpenAPI Community
