package com.example.spring_jpa_notes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    // Pagination & Sorting example will use built-in findAll(Pageable/Sort)

    // Named Query (JPQL)
    List<Student> findByMinAge(int minAge);

    // Named Native Query
    List<Student> findByDomainNative(String domain);
}
