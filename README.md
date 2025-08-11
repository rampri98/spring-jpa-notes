# Spring Data JPA – Basics & Core Concepts

## 1. What is JPA?
- **Java Persistence API (JPA)** is a **specification** for mapping Java objects to relational database tables.
- It is **not a framework** or implementation — it defines a set of interfaces and rules.
- Common JPA implementations:
    - **Hibernate** (most popular, default in Spring Boot)
    - EclipseLink, OpenJPA
- JPA itself doesn’t perform DB operations — it needs a provider (e.g., Hibernate).

**Example (JPA Entity)**
```java
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // getters & setters
}
```

---

## 2. JPA vs Hibernate vs JDBC
- Hibernate → A JPA implementation + its own extra proprietary features.
- Spring Data JPA → A Spring abstraction that uses JPA (and usually Hibernate under the hood) but hides a lot of boilerplate.

| Feature          | JPA (Specification)          | Hibernate (Implementation)    | JDBC (Low-Level API) |
|------------------|------------------------------|--------------------------------|----------------------|
| Type             | Specification                | Framework implementing JPA     | Java API for SQL     |
| SQL Writing      | Mostly hidden via JPQL       | Mostly hidden via JPQL         | Manual SQL writing   |
| Mapping          | Object–Relational Mapping    | ORM + extra features           | Manual ResultSet mapping |
| Productivity     | High                         | High                           | Low                  |
| Flexibility      | Depends on provider          | High                           | High but verbose     |

---

## 3. ORM Concepts in JPA

### a) Entities
- Java classes annotated with `@Entity`
- Represent a table in DB
- Fields = table columns

### b) Persistence Context
- A **session-like cache** where JPA entities are managed by the EntityManager.
- Ensures **automatic dirty checking** (changes in objects are tracked and updated in DB without explicit `UPDATE` calls).

### c) Transactions
- Group of operations that execute **atomically** (all or nothing).
- In Spring Data JPA:
    - Handled automatically with `@Transactional`
    - Ensures data consistency.

---

## 4. Role of Spring Data JPA
- **Goal**: Eliminate boilerplate code for repository/DAO layers.
- Extends JPA by providing:
    - Auto-implemented repository interfaces
    - Derived queries (method name-based)
    - Pagination and sorting support
- Works on top of JPA + a provider (Hibernate by default in Spring Boot).

**Example (Spring Data JPA Repository)**
```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Derived query method
    User findByName(String name);
}
```

---

## 5. Spring Data JPA Workflow
1. **Define Entity** → Annotate with `@Entity`
2. **Create Repository Interface** → Extend `JpaRepository`
3. **Use Repository in Service Layer** → Autowire and call methods
4. **Spring Boot Auto-Configures** the JPA provider (Hibernate by default)

---
1. [JDBC Notes](https://github.com/rampri98/spring-jpa-notes/tree/01-spring-jdbc)
2. [JPA Basics](https://github.com/rampri98/spring-jpa-notes/tree/02-spring-jpa-basics)
3. [Entity Mapping](https://github.com/rampri98/spring-jpa-notes/tree/03-spring-jpa-entity-mapping)
4. [Relationships](https://github.com/rampri98/spring-jpa-notes/tree/04-spring-jpa-relationships)
5. [Repositories](https://github.com/rampri98/spring-jpa-notes/tree/05-spring-jpa-repositories)
6. [Query Features](https://github.com/rampri98/spring-jpa-notes/tree/06-spring-jpa-query-features)
7. [Optimization](https://github.com/rampri98/spring-jpa-notes/tree/07-spring-jpa-optimization)
8. [Transactions](https://github.com/rampri98/spring-jpa-notes/tree/08-spring-jpa-transactions)
