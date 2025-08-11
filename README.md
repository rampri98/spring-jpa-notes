# Spring Data JPA – Query Features

## 1. Pagination
- Used to split large results into smaller **pages**.
- Key interfaces/classes:
  - `Pageable` → Encapsulates page number, size, sorting.
  - `Page<T>` → Contains content + pagination metadata.
  - `Slice<T>` → Similar to `Page`, but only knows if the next slice exists.

```java
Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());
Page<User> page = userRepository.findAll(pageable);

List<User> users = page.getContent();
int totalPages = page.getTotalPages();
long totalElements = page.getTotalElements();
```

- **Slice Example** (less overhead, no total count query):
```java
Slice<User> slice = userRepository.findAll(pageable);
boolean hasNext = slice.hasNext();
```

---

## 2. Sorting
- `Sort` specifies sorting parameters.
- Can be combined with `Pageable` or used independently.

```java
List<User> users = userRepository.findAll(Sort.by("name").descending());
```

- Multiple sort criteria:
```java
Sort sort = Sort.by("name").ascending()
                .and(Sort.by("age").descending());
```

---

## 3. Dynamic Queries – `Specification`
- **`Specification<T>`** allows building dynamic queries programmatically.
- Uses JPA Criteria API under the hood.
- Common for **complex filters**.

```java
public class UserSpecifications {
    public static Specification<User> hasName(String name) {
        return (root, query, cb) -> cb.equal(root.get("name"), name);
    }
}
```

```java
List<User> results = userRepository.findAll(
    Specification.where(UserSpecifications.hasName("Alice"))
);
```

- Repository must extend `JpaSpecificationExecutor<T>`.

```java
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> { }
```

---

## 4. Dynamic Queries – `Example` (Query by Example)
- Simpler than `Specification`, matches based on **probe object**.
- Useful for **simple matching**.

```java
User probe = new User();
probe.setName("Alice");
Example<User> example = Example.of(probe);

List<User> results = userRepository.findAll(example);
```

- Can customize matching behavior with `ExampleMatcher`.

```java
ExampleMatcher matcher = ExampleMatcher.matching()
        .withIgnoreCase()
        .withStringMatcher(StringMatcher.CONTAINING);
Example<User> example = Example.of(probe, matcher);
```

---

## 5. Named Queries
- Predefined queries annotated in entity classes.
- **`@NamedQuery`** → JPQL.
- **`@NamedNativeQuery`** → Native SQL.

```java
@Entity
@NamedQuery(
    name = "User.findByEmail",
    query = "SELECT u FROM User u WHERE u.email = ?1"
)
@NamedNativeQuery(
    name = "User.findByEmailNative",
    query = "SELECT * FROM users WHERE email = ?1",
    resultClass = User.class
)
public class User { ... }
```

- Usage:
```java
List<User> users = entityManager.createNamedQuery("User.findByEmail", User.class)
                                .setParameter(1, "test@example.com")
                                .getResultList();
```

---

## 6. Workflow Summary
1. Use **Pagination + Sorting** for large datasets.
2. Use `Specification` for complex and dynamic filtering.
3. Use `Example` for simple query-by-example patterns.
4. Use **Named Queries** for reusable, predefined JPQL/SQL.

---

## 7. Interview Tips
- **Page** gives total elements & total pages, **Slice** does not.
- `Specification` → More flexible, better for complex conditions.
- `Example` → Quick and simple, but less powerful.
- Prefer **JPQL named queries** over native ones for portability.
