package com.example.spring_jpa_notes.repositories;

import com.example.spring_jpa_notes.entities.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {}
