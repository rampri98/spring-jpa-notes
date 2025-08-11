package com.example.spring_jpa_notes.repos;

import com.example.spring_jpa_notes.entities.Student;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CachedStudentRepo extends JpaRepository<Student, Long> {
    @Cacheable("studentCache")
    Student findByName(String name);
}
