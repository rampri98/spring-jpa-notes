# Spring Data JPA – Transactions & Locking

## 1. `@Transactional` Behavior

### Basics
- Marks a method or class to run inside a **transaction**.
- By default:
  - **Rollback** occurs on `RuntimeException` and `Error`.
  - **No rollback** for checked exceptions (unless configured).
- Can be placed at **service layer** (best practice).

```java
@Service
public class UserService {

    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }
}
```

### Common Properties
- `readOnly = true` → Optimizes queries for read operations.
- `rollbackFor = Exception.class` → Rollback for checked exceptions too.
- `timeout = 5` → Rollback if transaction exceeds 5 seconds.

---

## 2. Isolation Levels

### Purpose
- Defines how transaction integrity is maintained with concurrent transactions.

| Level                | Prevents                        | Notes |
|----------------------|----------------------------------|-------|
| READ_UNCOMMITTED     | Nothing                         | Allows dirty reads |
| READ_COMMITTED       | Dirty reads                     | Default for many DBs |
| REPEATABLE_READ      | Dirty & non-repeatable reads    | May still allow phantom reads |
| SERIALIZABLE         | Dirty, non-repeatable, phantom  | Slowest but safest |

```java
@Transactional(isolation = Isolation.REPEATABLE_READ)
public void processOrder() { }
```

---

## 3. Propagation Types

| Type               | Behavior |
|--------------------|----------|
| REQUIRED (default) | Join existing transaction or create new if none |
| REQUIRES_NEW       | Always start a new transaction |
| MANDATORY          | Must join existing, else throw exception |
| SUPPORTS           | Join if exists, else run non-transactional |
| NOT_SUPPORTED      | Run outside transaction |
| NEVER              | Throw exception if transaction exists |
| NESTED             | Create nested transaction (savepoint) |

```java
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void logAudit(Audit audit) { }
```

---

## 4. Locking in JPA

### Optimistic Locking (`@Version`)
- Prevents **lost updates** by checking a version column.
- If another transaction updates the row, current transaction fails with `OptimisticLockException`.

```java
@Entity
public class Product {
    @Id
    private Long id;

    @Version
    private int version;
}
```

### Pessimistic Locking
- Locks database row until transaction ends.
- Prevents other transactions from reading/updating.

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("SELECT p FROM Product p WHERE p.id = :id")
Product findByIdForUpdate(@Param("id") Long id);
```

| Lock Mode                  | Behavior |
|----------------------------|----------|
| PESSIMISTIC_READ           | Prevents writes but allows reads |
| PESSIMISTIC_WRITE          | Prevents both reads & writes |
| PESSIMISTIC_FORCE_INCREMENT| Also increments version field |

---

## 5. Best Practices
- Keep transactions **short** to reduce lock contention.
- Use **optimistic locking** for high-read scenarios.
- Use **pessimistic locking** only when data integrity is critical & contention is high.
- Avoid placing `@Transactional` directly on repository methods; use service layer.
---
1. [JDBC Notes](https://github.com/rampri98/spring-jpa-notes/tree/01-spring-jdbc)
2. [JPA Basics](https://github.com/rampri98/spring-jpa-notes/tree/02-spring-jpa-basics)
3. [Entity Mapping](https://github.com/rampri98/spring-jpa-notes/tree/03-spring-jpa-entity-mapping)
4. [Relationships](https://github.com/rampri98/spring-jpa-notes/tree/04-spring-jpa-relationships)
5. [Repositories](https://github.com/rampri98/spring-jpa-notes/tree/05-spring-jpa-repositories)
6. [Query Features](https://github.com/rampri98/spring-jpa-notes/tree/06-spring-jpa-query-features)
7. [Optimization](https://github.com/rampri98/spring-jpa-notes/tree/07-spring-jpa-optimization)
8. [Transactions](https://github.com/rampri98/spring-jpa-notes/tree/08-spring-jpa-transactions)
