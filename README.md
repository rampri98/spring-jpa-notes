# Spring Data JPA – Relationships

## 1. Introduction to Relationships
- **Goal**: Define how entities relate to each other in the database.
- Mapped using **JPA annotations**:
  - `@OneToOne`
  - `@OneToMany`
  - `@ManyToOne`
  - `@ManyToMany`
- Relationship types reflect **database foreign key constraints**.

---

## 2. One-to-One (`@OneToOne`)
- **Definition**: One entity is related to exactly one other entity.
- **Example**: User ↔ Profile
- Usually implemented with a **foreign key in one table** pointing to another.

```java
@Entity
public class User {
    @OneToOne
    @JoinColumn(name = "profile_id") // FK column
    private Profile profile;
}
```

---

## 3. One-to-Many / Many-to-One
### One-to-Many (`@OneToMany`)
- One record in the parent → Many records in the child.
- Usually **bidirectional** with a `@ManyToOne` in the child.

```java
@Entity
public class Department {
    @OneToMany(mappedBy = "department") // 'department' field in Employee
    private List<Employee> employees;
}
```

### Many-to-One (`@ManyToOne`)
- Many records in the child → One record in the parent.
- The owning side usually holds the **foreign key**.

```java
@Entity
public class Employee {
    @ManyToOne
    @JoinColumn(name = "department_id") // FK column
    private Department department;
}
```

---

## 4. Many-to-Many (`@ManyToMany`)
- Many records in one entity relate to many in another.
- Implemented with a **join table**.

```java
@Entity
public class Student {
    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
}
```

---

## 5. `mappedBy`
- Defines the **inverse side** of a bidirectional relationship.
- The side with `mappedBy` **does not own the foreign key**.
- **Owning side** → defines `@JoinColumn` or `@JoinTable`.
- **Inverse side** → uses `mappedBy` to point to owning side’s field.

---

## 6. Cascade Types
- Control how operations propagate to related entities.
- `CascadeType` options:
  - `PERSIST` → Saves related entities automatically
  - `MERGE` → Updates related entities automatically
  - `REMOVE` → Deletes related entities automatically
  - `REFRESH` → Reloads related entities
  - `DETACH` → Detaches related entities
  - `ALL` → All of the above

```java
@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
private List<Employee> employees;
```

---

## 7. Fetch Types
- **Lazy** (`FetchType.LAZY`) → Loads relationship only when accessed (default for collections).
- **Eager** (`FetchType.EAGER`) → Loads relationship immediately (default for single-valued associations like `@OneToOne`).

```java
@ManyToOne(fetch = FetchType.LAZY)
private Department department;
```

---

## 8. Bidirectional vs Unidirectional
- **Unidirectional**:
  - Only one entity knows about the relationship.
  - Simpler, less maintenance.
- **Bidirectional**:
  - Both entities are aware.
  - Must maintain both sides in code to avoid inconsistencies.

---

## 9. Join Tables & Join Columns
- **`@JoinColumn`** → Maps a foreign key column.
  - Used in `@OneToOne` and `@ManyToOne`.
- **`@JoinTable`** → Defines an intermediate join table.
  - Used in `@ManyToMany` or sometimes `@OneToMany` with a join table.

---

## 10. Workflow Summary
1. Choose the right **relationship annotation**.
2. Decide **owning side** vs **inverse side** (`mappedBy`).
3. Configure **cascades** if needed.
4. Choose **fetch strategy** wisely (default lazy for collections).
5. Use **join columns/tables** for mapping in DB.
---
1. [JDBC Notes](https://github.com/rampri98/spring-jpa-notes/tree/01-spring-jdbc)
2. [JPA Basics](https://github.com/rampri98/spring-jpa-notes/tree/02-spring-jpa-basics)
3. [Entity Mapping](https://github.com/rampri98/spring-jpa-notes/tree/03-spring-jpa-entity-mapping)
4. [Relationships](https://github.com/rampri98/spring-jpa-notes/tree/04-spring-jpa-relationships)
5. [Repositories](https://github.com/rampri98/spring-jpa-notes/tree/05-spring-jpa-repositories)
6. [Query Features](https://github.com/rampri98/spring-jpa-notes/tree/06-spring-jpa-query-features)
7. [Optimization](https://github.com/rampri98/spring-jpa-notes/tree/07-spring-jpa-optimization)
8. [Transactions](https://github.com/rampri98/spring-jpa-notes/tree/08-spring-jpa-transactions)
