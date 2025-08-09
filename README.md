# Spring JDBC Essentials for JPA Developers

## 1. Core Concepts

### What JDBC is
- **JDBC (Java Database Connectivity)** is a Java API that allows Java programs to connect and execute queries with databases.
- It provides a standard interface for database interaction but is low-level and verbose.

### Limitations of Raw JDBC
- Manual resource management (open/close connections, statements, result sets).
- Complex error handling (SQLException handling).
- Boilerplate code for even simple queries.

### How Spring Simplifies JDBC
- **Spring JDBC** abstracts away repetitive code using `JdbcTemplate` and other utilities.
- Handles resource management and exception translation automatically.

### DataSource and Connection Pools
- **DataSource**: A factory for database connections.
- **Connection Pools** (e.g., HikariCP): Reuse existing connections instead of creating new ones for every request, improving performance.
- Spring Boot uses HikariCP by default.

---

## 2. Spring JDBC Setup

### Adding Dependency
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```

### Configuring Database Properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=secret
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### Auto-Configuration
- Spring Boot auto-configures a `DataSource` bean and a `JdbcTemplate` bean if `spring-boot-starter-jdbc` is present.
- Default connection pool: **HikariCP**.

---

## 3. JdbcTemplate Basics

### Purpose
- Simplifies JDBC operations.
- Automatically handles connections, statements, and result sets.
- Converts checked SQL exceptions into runtime exceptions.

### Common Methods
```java
// queryForObject - Single value or single row
String name = jdbcTemplate.queryForObject(
    "SELECT name FROM users WHERE id = ?", String.class, 1);

// query - Multiple rows
List<User> users = jdbcTemplate.query(
    "SELECT * FROM users",
    new RowMapper<User>() {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getInt("id"), rs.getString("name"));
        }
    }
);

// update - INSERT/UPDATE/DELETE
int rows = jdbcTemplate.update(
    "UPDATE users SET name = ? WHERE id = ?", "John", 1);

// batchUpdate - Batch operations
jdbcTemplate.batchUpdate(
    "INSERT INTO users (name) VALUES (?)",
    List.of(new Object[]{"Alice"}, new Object[]{"Bob"})
);
```

---

## 4. Mapping Database Results

### RowMapper Interface
- Allows manual mapping from a ResultSet to a Java object.

### BeanPropertyRowMapper
- Automatically maps columns to Java properties by name.
```java
List<User> users = jdbcTemplate.query(
    "SELECT * FROM users",
    new BeanPropertyRowMapper<>(User.class)
);
```

### Mapping to DTOs
- Use `RowMapper` or projections to map to DTOs instead of entities.

---

## 5. Parameter Handling

### Positional Parameters
```java
jdbcTemplate.queryForObject(
    "SELECT name FROM users WHERE id = ?", String.class, 1
);
```

### Named Parameters
- `NamedParameterJdbcTemplate` allows named placeholders.
```java
NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(dataSource);
Map<String, Object> params = Map.of("id", 1);
String name = namedTemplate.queryForObject(
    "SELECT name FROM users WHERE id = :id", params, String.class
);
```

---

## 6. Transactions

### @Transactional with JDBC
- Works the same as in JPA.
- Ensures a method executes within a single transaction.

### Commit and Rollback
- **Commit**: Save changes to the database.
- **Rollback**: Undo changes if an error occurs.
- Spring automatically commits or rolls back based on exceptions.

---

## 7. Error Handling

### DataAccessException
- Spring converts `SQLException` into a runtime exception hierarchy: `DataAccessException`.

### Why Spring Wraps SQLException
- Avoids mandatory try-catch blocks.
- Provides a consistent exception hierarchy across different databases.
---

## 8. Schema.sql
- **Purpose**: Defines the database structure (tables, indexes, constraints). 
- **Location**: Place in src/main/resources/ so Spring Boot auto-detects it. 
- **Execution**: Runs automatically before data.sql at startup. 
- **Use case**: Needed when using Spring JDBC (or in-memory DB) because JDBC doesn’t auto-generate tables like JPA. 
- **Disabling**: You can disable auto-run with: spring.sql.init.mode=never
```sql
CREATE TABLE employee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(50)
);

CREATE TABLE department (
    dept_id INT PRIMARY KEY AUTO_INCREMENT,
    dept_name VARCHAR(100) UNIQUE
);
```
