package com.example.spring_jpa_notes.repositories;

import com.example.spring_jpa_notes.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("""
    SELECT s FROM Student s
    LEFT JOIN FETCH s.classroom
    """)
    List<Student> findAllWithClassroom();

}