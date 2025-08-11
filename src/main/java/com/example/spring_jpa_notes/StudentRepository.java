package com.example.spring_jpa_notes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Derived query methods (naming convention)
    List<Student> findByName(String name);
    List<Student> findByAgeBetween(int minAge, int maxAge);
    List<Student> findByEmailContainingIgnoreCase(String keyword);

    // JPQL Query
    @Query("SELECT s FROM Student s WHERE s.age > :age")
    List<Student> getStudentsOlderThan(int age);

    // Native SQL Query
    @Query(value = "SELECT * FROM student WHERE email LIKE %:domain%", nativeQuery = true)
    List<Student> searchByEmailDomain(String domain);

    // Update query (modifying)
    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.email = :email WHERE s.id = :id")
    int updateEmailById(Long id, String email);

    // Delete query (modifying)
    @Modifying
    @Transactional
    @Query("DELETE FROM Student s WHERE s.age < :age")
    int deleteStudentsYoungerThan(int age);
}
