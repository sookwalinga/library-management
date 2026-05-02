# Library Management System

A Spring Boot application for managing **Books** and **Authors** with full CRUD support (Create, Read, Update), JPA relationships, JSP views, and unit tests.

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA / Hibernate**
- **JSP + JSTL** for views
- **H2 Database** (in-memory, default) — switchable to MySQL
- **JUnit 5 + Mockito** for testing
- **Maven** for build management
- **Lombok** for boilerplate reduction

## Entity Relationship Design

``` text
┌────────────────────┐                ┌────────────────────┐
│      Author        │  1          *  │       Book         │
├────────────────────┤◄───────────────┤────────────────────┤
│ id (PK)            │   @OneToMany   │ id (PK)            │
│ name               │                │ title              │
│ nationality        │                │ isbn (UNIQUE)      │
│ birth_year         │                │ publication_year   │
│ biography          │                │ genre              │
└────────────────────┘                │ price              │
                                      │ author_id (FK)     │
                                      └────────────────────┘
```

- **Author → Book**: One-to-Many
- **Book → Author**: Many-to-One
- The `books.author_id` foreign key cannot be null.
- The `books.isbn` column is unique.

## Project Structure

``` text
library-management/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/library/
│   │   │   ├── LibraryManagementApplication.java
│   │   │   ├── config/
│   │   │   │   └── DataInitializer.java
│   │   │   ├── controller/
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── AuthorController.java
│   │   │   │   └── BookController.java
│   │   │   ├── entity/
│   │   │   │   ├── Author.java
│   │   │   │   ├── Book.java
│   │   │   │   └── BookAuthorDTO.java
│   │   │   ├── exception/
│   │   │   │   ├── DataIntegrityException.java
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   ├── repository/
│   │   │   │   ├── AuthorRepository.java
│   │   │   │   └── BookRepository.java
│   │   │   └── service/
│   │   │       ├── AuthorService.java
│   │   │       └── BookService.java
│   │   ├── resources/
│   │   │   └── application.properties
│   │   └── webapp/
│   │       ├── resources/css/styles.css
│   │       └── WEB-INF/views/
│   │           ├── home.jsp
│   │           ├── authors/{list,form,edit}.jsp
│   │           └── books/{list,form,edit}.jsp
│   └── test/java/com/library/
│       ├── repository/{AuthorRepositoryTest,BookRepositoryTest}.java
│       └── service/{AuthorServiceTest,BookServiceTest}.java
```

## Running the Application

### Prerequisites

- JDK 17+
- Maven 3.6+

### Build & Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start at: **<http://localhost:8080>**

### URL Map

| URL                                  | Description                       |
|--------------------------------------|-----------------------------------|
| `/`                                  | Home dashboard                    |
| `/authors`                           | List all authors                  |
| `/authors/new`                       | Form to add a new author          |
| `/authors/edit/{id}`                 | Form to edit an author            |
| `/books`                             | List all books (INNER JOIN view)  |
| `/books?genre=Fantasy`               | Filter list by genre              |
| `/books/new`                         | Form to add a new book            |
| `/books/edit/{id}`                   | Form to edit a book               |
| `/h2-console`                        | H2 database console               |

## Sample Data

The application is preloaded with **10 authors** and **10 books** on startup
(see `DataInitializer.java`).

## Custom INNER JOIN Query

Inside `BookRepository.java`:

```java
@Query("SELECT new com.library.entity.BookAuthorDTO(" +
       "b.id, b.title, b.isbn, b.publicationYear, b.genre, b.price, " +
       "a.id, a.name, a.nationality) " +
       "FROM Book b INNER JOIN b.author a " +
       "ORDER BY a.name ASC, b.title ASC")
List<BookAuthorDTO> findAllBooksWithAuthors();
```

## Running Tests

```bash
mvn test
```

Tests included:

- `AuthorRepositoryTest` — `@DataJpaTest` against in-memory H2
- `BookRepositoryTest` — includes a duplicate-ISBN integrity test
- `AuthorServiceTest` — Mockito-based unit tests
- `BookServiceTest` — Mockito-based unit tests, including update logic

## Switching to MySQL

Edit `src/main/resources/application.properties`, comment out the H2 lines and
uncomment the MySQL section, then update the username/password.
