# Spring Data JPA – Performance & Optimization
Note: CODE DOES NOT WORK
## 1. Lazy vs Eager Fetching

### Lazy (Default for collections)
- Associated entities are **loaded only when accessed**.
- Reduces initial query load.
- Risk: accessing outside a transaction causes `LazyInitializationException`.

```java
@Entity
public class User {
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders;
}
```

### Eager
- Loads associated entities **immediately** with the parent entity.
- Increases initial query time & memory usage.
- Use **only** when related data is always needed.

```java
@Entity
public class User {
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Order> orders;
}
```

---

## 2. N+1 Query Problem

### What it is:
- Occurs when fetching a parent list triggers **one query for parents + one query per child**.
- Example: Fetching 10 users with orders → 1 query for users + 10 queries for orders.

### Solutions:

#### a) Fetch Joins (JPQL)
- Loads related entities in a single query.

```java
@Query("SELECT u FROM User u JOIN FETCH u.orders")
List<User> findAllWithOrders();
```

#### b) `@EntityGraph`
- Declaratively specify which relationships to load.

```java
@EntityGraph(attributePaths = {"orders"})
List<User> findAll();
```

---

## 3. Caching

### First-Level Cache (Persistence Context)
- Default in JPA/Hibernate.
- EntityManager keeps loaded entities in memory until transaction ends.
- Transparent to developer; cannot be disabled.

### Second-Level Cache
- Stores entities across multiple sessions.
- Requires enabling & configuring cache provider (e.g., Ehcache, Hazelcast).
- Enabled in `application.properties`:
```properties
spring.jpa.properties.hibernate.cache.use_second_level_cache=true
spring.jpa.properties.hibernate.cache.region.factory_class=org.hibernate.cache.ehcache.EhCacheRegionFactory
```

- Example:
```java
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User {}
```

### Query Cache
- Stores query results.
- Must enable second-level cache first.
```properties
spring.jpa.properties.hibernate.cache.use_query_cache=true
```

```java
List<User> users = entityManager.createQuery("FROM User")
    .setHint("org.hibernate.cacheable", true)
    .getResultList();
```

---

## 4. Best Practices
- Default to **LAZY** fetching for relationships.
- Use **JOIN FETCH** or **@EntityGraph** to avoid N+1 queries.
- Cache **read-mostly** data using second-level cache.
- Avoid eager loading unless you always need the data.
- Profile queries using Hibernate’s `show_sql` or tools like p6spy.

---

## 5. What this demonstrates
- Lazy vs Eager fetching — controlling when related entities are loaded.
- N+1 problem — why it happens and how to fix it (@EntityGraph / fetch joins).
- Caching
  - First-level cache: Always present in Hibernate session (automatic).
  - Second-level cache: Shared across sessions (requires config).
  - Query cache: Stores query results, requires explicit enabling.
---
1. [JDBC Notes](https://github.com/rampri98/spring-jpa-notes/tree/01-spring-jdbc)
2. [JPA Basics](https://github.com/rampri98/spring-jpa-notes/tree/02-spring-jpa-basics)
3. [Entity Mapping](https://github.com/rampri98/spring-jpa-notes/tree/03-spring-jpa-entity-mapping)
4. [Relationships](https://github.com/rampri98/spring-jpa-notes/tree/04-spring-jpa-relationships)
5. [Repositories](https://github.com/rampri98/spring-jpa-notes/tree/05-spring-jpa-repositories)
6. [Query Features](https://github.com/rampri98/spring-jpa-notes/tree/06-spring-jpa-query-features)
7. [Optimization](https://github.com/rampri98/spring-jpa-notes/tree/07-spring-jpa-optimization)
8. [Transactions](https://github.com/rampri98/spring-jpa-notes/tree/08-spring-jpa-transactions)

