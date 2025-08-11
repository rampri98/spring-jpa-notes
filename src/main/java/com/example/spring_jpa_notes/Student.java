package com.example.spring_jpa_notes;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@NamedQuery(
        name = "Student.findByMinAge",
        query = "SELECT s FROM Student s WHERE s.age >= :minAge"
)
@NamedNativeQuery(
        name = "Student.findByDomainNative",
        query = "SELECT * FROM student WHERE email LIKE %:domain%",
        resultClass = Student.class
)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String email;

    public Student() {}
    public Student(Long id, String name, int age, String email) {
        this.id = id; this.name = name; this.age = age; this.email = email;
    }
}
