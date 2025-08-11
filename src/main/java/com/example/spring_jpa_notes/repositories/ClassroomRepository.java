package com.example.spring_jpa_notes.repositories;

import com.example.spring_jpa_notes.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {}
