package com.example.spring_jpa_notes.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // If bidirectional, we map back here
    @ManyToMany(mappedBy = "teachers")
    private List<Course> courses = new ArrayList<>();
}
