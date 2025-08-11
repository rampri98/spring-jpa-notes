# Spring Data JPA – Entity Mapping

## 1. Entity Basics
### `@Entity`
- Marks a class as a **JPA entity** (maps to a DB table).
- Must have:
  - A no-argument constructor (can be `protected`).
  - A primary key field.
- The class **must not be final** and **fields must be non-final** for proxying.

```java
import jakarta.persistence.*;

@Entity
public class User {
    // fields, getters, setters
}
```

---

## 2. Table Mapping
### `@Table`
- Customizes table details.
- Common attributes:
  - `name` → table name in DB
  - `schema` → schema name (if applicable)
  - `uniqueConstraints` → for unique combinations

```java
@Entity
@Table(name = "users", schema = "app_schema")
public class User {  }
```

---

## 3. Primary Key Mapping
### `@Id`
- Marks the **primary key** field.

### `@GeneratedValue`
- Configures primary key generation strategy.
- Strategies:
  - `AUTO` → Provider chooses (default)
  - `IDENTITY` → Auto-increment by DB
  - `SEQUENCE` → Uses DB sequence
  - `TABLE` → Uses table to generate keys

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

---

## 4. Column Mapping
### `@Column`
- Customizes how a field maps to a column.
- Common attributes:
  - `name` → DB column name
  - `nullable` → `true` (default) or `false`
  - `length` → Max size (for `VARCHAR`)
  - `unique` → Adds unique constraint

```java
@Column(name = "email_address", nullable = false, unique = true, length = 100)
private String email;
```

---

## 5. Transient Fields
### `@Transient`
- Field is **not persisted** to the database.
- Used for calculated or temporary values.

```java
@Transient
private int sessionLoginAttempts;
```

---

## 6. Embedded Objects
- Helps group related fields into reusable components.

### `@Embeddable`
- Marks a class whose fields are **mapped into the entity’s table**.

```java
@Embeddable
public class Address {
    private String street;
    private String city;
}
```

### `@Embedded`
- Used inside an entity to include the embeddable object.

```java
@Entity
public class User {
    @Embedded
    private Address address;
}
```

---

## 7. Workflow Summary
1. **Annotate** with `@Entity`
2. **Map table** with `@Table` (optional)
3. **Define primary key** with `@Id` + `@GeneratedValue`
4. **Customize columns** with `@Column`
5. **Exclude non-persistent** fields with `@Transient`
6. **Reuse field groups** with `@Embeddable` + `@Embedded`
