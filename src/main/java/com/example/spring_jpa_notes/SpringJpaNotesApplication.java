package com.example.spring_jpa_notes;

import com.example.spring_jpa_notes.entities.Course;
import com.example.spring_jpa_notes.entities.Enrollment;
import com.example.spring_jpa_notes.entities.Student;
import com.example.spring_jpa_notes.repos.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringJpaNotesApplication  implements CommandLineRunner  {

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaNotesApplication.class, args);
	}

//	@Autowired
//	private StudentRepo studentRepo;

	@Override
	public void run(String... args) {
		// Create data
//		Course c1 = new Course(); c1.setTitle("Mathematics");
//		Course c2 = new Course(); c2.setTitle("Science");
//
//		Student s1 = new Student(); s1.setName("Alice");
//		Student s2 = new Student(); s2.setName("Bob");
//
//		Enrollment e1 = new Enrollment(); e1.setStudent(s1); e1.setCourse(c1); e1.setGrade("A");
//		Enrollment e2 = new Enrollment(); e2.setStudent(s1); e2.setCourse(c2); e2.setGrade("B");
//		Enrollment e3 = new Enrollment(); e3.setStudent(s2); e3.setCourse(c1); e3.setGrade("A+");
//
//		s1.getEnrollments().add(e1); s1.getEnrollments().add(e2);
//		s2.getEnrollments().add(e3);
//
//		studentRepo.save(s1);
//		studentRepo.save(s2);

		// ❌ N+1 Problem example
//		System.out.println("\n[Without Optimization] N+1 Problem:");
//		studentRepo.findAll().forEach(s -> {
//			System.out.println("Student: " + s.getName());
//			s.getEnrollments().forEach(en -> System.out.println("  " + en.getCourse().getTitle()));
//		});

		// ✅ With EntityGraph
//		System.out.println("\n[With EntityGraph] Single query:");
//		studentRepo.findAllWithEnrollments().forEach(s -> {
//			System.out.println("Student: " + s.getName());
//			s.getEnrollments().forEach(en -> System.out.println("  " + en.getCourse().getTitle()));
//		});

		// ✅ With Fetch Join
//		System.out.println("\n[With Fetch Join] Single query:");
//		studentRepo.findAllWithEnrollmentsFetchJoin().forEach(s -> {
//			System.out.println("Student: " + s.getName());
//			s.getEnrollments().forEach(en -> System.out.println("  " + en.getCourse().getTitle()));
//		});
	}
}