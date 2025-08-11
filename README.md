# Spring Data JPA – Repositories

## 1. Repository Overview
- Spring Data JPA provides pre-built **repository interfaces** to eliminate boilerplate CRUD code.
- All repositories extend from the **`Repository`** marker interface.
- Main repository types:
  - `CrudRepository<T, ID>` → Basic CRUD operations.
  - `PagingAndSortingRepository<T, ID>` → CRUD + pagination & sorting.
  - `JpaRepository<T, ID>` → CRUD + pagination/sorting + JPA-specific features.

---

## 2. `CrudRepository`
- Generic interface for CRUD operations.
- Common methods:
  - `save(entity)` → Insert or update.
  - `findById(id)` → Retrieve one record.
  - `findAll()` → Retrieve all records.
  - `deleteById(id)` → Delete by ID.
  - `count()` → Count total records.

```java
public interface UserRepository extends CrudRepository<User, Long> { }
```

---

## 3. `PagingAndSortingRepository`
- Extends `CrudRepository` with pagination and sorting features.
- Key methods:
  - `findAll(Sort sort)`
  - `findAll(Pageable pageable)`

```java
Page<User> users = userRepository.findAll(
    PageRequest.of(0, 10, Sort.by("name"))
);
```

---

## 4. `JpaRepository`
- Extends `PagingAndSortingRepository` and `CrudRepository`.
- Adds methods like:
  - `flush()`
  - `saveAndFlush(entity)`
  - `deleteInBatch(entities)`
  - `getOne(id)`

```java
public interface UserRepository extends JpaRepository<User, Long> { }
```

---

## 5. Derived Query Methods
- Queries can be derived **from method names**.
- Naming convention: [Action]By[Field][Condition][Operator][And/Or][Field][Condition][Operator]...[OrderByFieldAsc/Desc]

- Examples:
  - `findByName(String name)` → `SELECT * FROM user WHERE name = ?`
  - `findByAgeGreaterThan(int age)`
  - `findByNameAndCity(String name, String city)`
- Common keywords: `And`, `Or`, `Between`, `LessThan`, `Like`, `OrderBy`.

```java
List<User> findByEmailContaining(String keyword);
```

---

## 6. `@Query` Annotation
- Define custom queries using JPQL or native SQL.
- JPQL → Uses entity names & fields.
- Native SQL → Uses table/column names.

```java
@Query("SELECT u FROM User u WHERE u.email = ?1")
List<User> findByEmailJPQL(String email);

@Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
List<User> findByEmailNative(String email);
```

---

## 7. Modifying Queries
- For **update** or **delete** operations.
- Must use `@Modifying` and `@Transactional`.

```java
@Transactional
@Modifying
@Query("UPDATE User u SET u.status = ?2 WHERE u.id = ?1")
int updateUserStatus(Long id, String status);
```

---

## 8. Workflow Summary
1. Choose the correct repository type (`JpaRepository` is most common).
2. Use **derived query methods** for simple queries.
3. Use `@Query` for complex/custom queries.
4. Use `@Modifying` with `@Transactional` for update/delete queries.
---
1. [JDBC Notes](https://github.com/rampri98/spring-jpa-notes/tree/01-spring-jdbc)
2. [JPA Basics](https://github.com/rampri98/spring-jpa-notes/tree/02-spring-jpa-basics)
3. [Entity Mapping](https://github.com/rampri98/spring-jpa-notes/tree/03-spring-jpa-entity-mapping)
4. [Relationships](https://github.com/rampri98/spring-jpa-notes/tree/04-spring-jpa-relationships)
5. [Repositories](https://github.com/rampri98/spring-jpa-notes/tree/05-spring-jpa-repositories)
6. [Query Features](https://github.com/rampri98/spring-jpa-notes/tree/06-spring-jpa-query-features)
7. [Optimization](https://github.com/rampri98/spring-jpa-notes/tree/07-spring-jpa-optimization)
8. [Transactions](https://github.com/rampri98/spring-jpa-notes/tree/08-spring-jpa-transactions)

