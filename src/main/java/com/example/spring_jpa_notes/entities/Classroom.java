package com.example.spring_jpa_notes.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Bidirectional One-to-Many
    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();

}
