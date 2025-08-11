package com.example.spring_jpa_notes.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    // Unidirectional Many-to-Many
    @ManyToMany
    @JoinTable(
            name = "course_teacher",
            joinColumns = @JoinColumn(name = "course_id"), // FK to course
            inverseJoinColumns = @JoinColumn(name = "teacher_id") // FK to teacher
    )
    private List<Teacher> teachers = new ArrayList<>();
}
