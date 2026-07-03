# Blog API - Secure Backend Service

A robust, production-ready RESTful API built with **Spring Boot** and **PostgreSQL** that implements a state-of-the-art, stateless security infrastructure using the modern **OAuth2 JWT Resource Server architecture**.

---

##  Features & Architecture

* **Stateless Authentication**: Uses signed JSON Web Tokens (JWT) for secure, scalable API security instead of vulnerable session cookies.
* **Asymmetric Encryption**: Automatically generates secure, 2048-bit RSA cryptographic key pairs in-memory using the Nimbus JOSE framework upon server startup.
* **Secure Data Hashing**: Translucently secures all raw user credentials using high-entropy **BCrypt password encoding** before persisting to the database.
* **Isolated Environment Variables**: Follows production security standards by extracting database credentials and critical settings completely out of the source code.

---

##  Technical Stack

* **Backend Framework**: Spring Boot 3.x
* **Security Specifications**: Spring Security (OAuth2 Resource Server, JWT Architecture)
* **Database**: PostgreSQL
* **ORM/Data Mapping**: Spring Data JPA / Hibernate
* **Build Tool**: Maven

---

##  Local Setup & Installation

### 1. Prerequisites
Ensure you have the following installed locally:
* Java 17 or higher
* PostgreSQL

### 2. Environment Variables Configuration
To keep your credentials secure, this application relies on environment variables. Set up the following keys in your IDE or local environment variables before running:

* `DB_USERNAME`: Your local PostgreSQL database username.
* `DB_PASSWORD`: Your local PostgreSQL database password.

### 3. Application Configuration (`application.properties`)
The database connection dynamically checks your environment variables:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5462/blogapi
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:}