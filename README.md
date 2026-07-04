#  Blog API - Advanced Secure Backend Service

A highly scalable, secure, and production-ready RESTful API backend for a Blogging Platform built using **Spring Boot**, **Spring Security (JWT)**, **Spring Data JPA**, and **PostgreSQL**. The system utilizes advanced **Aspect-Oriented Programming (AOP)** for background method profiling, complete request validation decoupling via **DTO design patterns**, and optimized relational database fetch controls.

---

##  Core Features & Architecture

* **Stateless Authentication (JWT):** Secure user onboarding using email handles with custom `OncePerRequestFilter` Bearer token interception mechanisms, eliminating vulnerable session cookies.
* **Cryptographic Protection:** Zero plain-text leaks via automatic high-entropy `BCryptPasswordEncoder` password salting and hashing before database persistence.
* **Isolated Environment Variables:** Follows production security standards by extracting database credentials and critical settings completely out of the source code.
* **Complex DB Relations:** Full multi-association relational grid maps including `ManyToOne` (Post ➔ Author, Comment ➔ Post) and `ManyToMany` via custom join tables (`post_categories`).
* **Query Performance Optimization:** Explicit `FetchType.LAZY` configurations on mappings preventing the critical $N+1$ selection performance degradation.
* **Automated Back-End Profiling (AOP):** Background `PerformanceLoggingAspect` intercepting application layers to trace system runtime execution metrics dynamically without bloating business code.
* **Infinite Recursion Prevention:** Integrated Jackson annotations (`@JsonIgnoreProperties`) breaking bidirectional circular reference loops during complex serialization.

---

##  Technical Stack

* **Backend Framework:** Spring Boot 3.x
* **Security Specifications:** Spring Security (Stateless Token Architecture, JWT Bearer Filters)
* **Database Engine:** PostgreSQL
* **ORM/Data Mapping:** Spring Data JPA / Hibernate
* **Build Tool:** Maven

---

##  Endpoints Matrix

###  Authentication & Onboarding
| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/users/register` | Public | Register a new user account via DTO container |
| `POST` | `/api/auth/login` | Public | Authenticate via email & receive signed JWT |
| `GET` | `/api/users/allUsers` | Protected | Retrieve all registered users |
| `GET` | `/api/users/find/{id}` | Protected | Fetch a specific user by Database ID |

###  Blog Posts Management
| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/posts/createPost` | Protected | Create a post and attach multiple categories |
| `GET` | `/api/posts/allPosts` | Protected | Retrieve all blog entries with linked categories |
| `GET` | `/api/posts/slug/{slug}` | Protected | Fetch an SEO-friendly blog post via unique slug |
| `PUT` | `/api/posts/update/{id}` | Protected | Modify title (regenerates slug), body, or category mappings |
| `DELETE` | `/api/posts/delete/{id}` | Protected | Remove a blog post from the system database |

###  Categories & 💬 Comments
| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/categories/createCategory` | Protected | Instantiate a unique blog category tag |
| `GET` | `/api/categories/allCategories` | Protected | Fetch all tag groupings |
| `POST` | `/api/comments/addComment` | Protected | Post a comment linked to a specific Post ID |
| `GET` | `/api/comments/post/{postId}` | Protected | Fetch all discussion items matching a specific post |

---

##  Project Architecture Grid

```text
src/main/java/com/ujjwal/blogapi/
│
├── aspect/          # PerformanceLoggingAspect (AOP Profiling & Execution Timings)
├── config/          # SecurityConfig (Filter Chain Settings & Bean Directives)
├── controller/      # REST API Controllers (Endpoints Presentation Layer)
├── dto/             # Data Transfer Objects (Request/Response Separation Containers)
├── model/           # JPA Entities (PostgreSQL Relational Schema Mapping)
├── repository/      # Data Access Layers (Automated JPA Query Generation)
└── security/        # CustomUserDetailsService, JwtTokenProvider, JwtAuthenticationFilter
```

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