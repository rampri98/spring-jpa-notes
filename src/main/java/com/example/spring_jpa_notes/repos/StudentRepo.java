package com.example.spring_jpa_notes.repos;

import com.example.spring_jpa_notes.entities.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    // ❌ This can cause N+1 problem (when accessing enrollments for each student)
    List<Student> findAll();

    // ✅ Fix with EntityGraph (fetch enrollments + course in single query)
    @EntityGraph(attributePaths = {"enrollments", "enrollments.course"})
    List<Student> findAllWithEnrollments();

    // ✅ Fix with JPQL fetch join
    @Query("SELECT DISTINCT s FROM Student s " +
            "LEFT JOIN FETCH s.enrollments e " +
            "LEFT JOIN FETCH e.course")
    List<Student> findAllWithEnrollmentsFetchJoin();

    Student findByName(String name);
}
