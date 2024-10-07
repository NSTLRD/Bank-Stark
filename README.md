# Bank-Stark

Bank-Stark is an open-source application designed to manage and support banking functionalities. It is built using Spring Boot, providing essential features such as user management, transaction handling, and account security through JWT-based authentication. This project is a part of Mentorly Academy's initiative to build educational and practical resources for learning modern Java backend technologies.

# Features

- **User Registration and Authentication**: Supports user registration, login, and account activation via email using Spring Mail.
- **JWT Authentication**: Secure user sessions using JSON Web Tokens (JWT).
- **Thymeleaf Integration**: Provides front-end rendering with Thymeleaf for easy integration of server-side dynamic content.
- **Spring Data JPA**: Enables the use of JPA repositories for database interaction.
- **MySQL & H2 Database Support**: Choose between H2 (in-memory) or MySQL for data persistence.
- **Spring Security**: Built-in security layer for role-based access control and authorization.
- **OpenAPI Documentation**: Auto-generated API documentation with Springdoc OpenAPI integration.
- **Profiles for Dev, Test, and Prod**: Configured for different environments through Maven profiles.

# Technologies Used

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Security**
- **Spring Data JPA**
- **JWT (JSON Web Token)**
- **Thymeleaf**
- **H2 Database**
- **MySQL**
- **Lombok**
- **Spring Mail**
- **OpenAPI (Springdoc)**

# Installation

# Prerequisites

- Java 17+
- Maven
- MySQL (optional, if using MySQL as a database)

# Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/NSTLRD/bank-stark.git
   ```
2. Navigate to the project directory:
   ```bash
   cd bank-stark
   ```
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
5. Access the application at `http://localhost:8080`.

# API Documentation

The OpenAPI documentation is available at:  
`http://localhost:8080/swagger-ui.html`

# Usage

Once the application is running, users can:

- Register a new account.
- Activate their account via the email sent to them.
- Log in to access protected endpoints using JWT tokens.

# Profiles

The project includes the following profiles:

- **Dev**: Used for local development.
- **Test**: Used for running tests.
- **Prod**: For production environment.

To run the application in a specific profile, use:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=<profile>
```

# Contact and Social Links

- **GitHub**: [Starling Diaz](https://github.com/NSTLRD)
- **Website**: [Mentorly Blog](https://mentorly.blog/)
- **Linkedin**: [Linkedin](https://www.linkedin.com/in/starling-diaz-908225181/)
- **Mentorly Academy**: [Mentorly Academy](https://www.mentor-ly.com/)
- **Youtube**: [Mentorly Youtube](https://www.youtube.com/@Mentorly-e3b)
---

**Version:** 0.0.1-SNAPSHOT  
**Author:** Starling Diaz  
**License:** Open Source
```

This README provides a comprehensive overview of the project, including installation steps, technologies, and features.