package com.example.spring_jpa_notes.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class StudentProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bio;

    // Unidirectional One-to-One
    @OneToOne
    @JoinColumn(name = "student_id") // Foreign key in profile table
    private Student student;
}
