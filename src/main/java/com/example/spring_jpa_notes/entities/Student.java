package com.example.spring_jpa_notes.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Bidirectional Many-to-One (Student → Classroom)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id") // Foreign key in student table
    private Classroom classroom;

    // Bidirectional One-to-Many (Student ↔ Enrollment)
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();
}
