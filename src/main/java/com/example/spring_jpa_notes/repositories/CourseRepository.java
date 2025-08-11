package com.example.spring_jpa_notes.repositories;

import com.example.spring_jpa_notes.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {}
