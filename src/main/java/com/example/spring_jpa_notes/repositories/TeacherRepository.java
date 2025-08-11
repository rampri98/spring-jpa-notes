package com.example.spring_jpa_notes.repositories;

import com.example.spring_jpa_notes.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {}
