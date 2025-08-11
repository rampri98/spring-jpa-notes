package com.example.spring_jpa_notes.repositories;

import com.example.spring_jpa_notes.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {}
