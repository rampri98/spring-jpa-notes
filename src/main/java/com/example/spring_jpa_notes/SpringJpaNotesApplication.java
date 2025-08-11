package com.example.spring_jpa_notes;

import com.example.spring_jpa_notes.entities.*;
import com.example.spring_jpa_notes.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringJpaNotesApplication  implements CommandLineRunner  {

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaNotesApplication.class, args);
	}

	@Autowired private StudentRepository studentRepo;
	@Autowired private StudentProfileRepository profileRepo;
	@Autowired private ClassroomRepository classroomRepo;
	@Autowired private CourseRepository courseRepo;
	@Autowired private TeacherRepository teacherRepo;
	@Autowired private EnrollmentRepository enrollmentRepo;

	@Override
	public void run(String... args) {
		// Create Classroom
		Classroom classroom = new Classroom();
		classroom.setName("Class A");
		classroomRepo.save(classroom);

		// Create Students
		Student s1 = new Student();
		s1.setName("Alice");
		s1.setClassroom(classroom);

		Student s2 = new Student();
		s2.setName("Bob");
		s2.setClassroom(classroom);

		classroom.setStudents(Arrays.asList(s1, s2));
		studentRepo.saveAll(Arrays.asList(s1, s2));

		// Create Student Profiles (One-to-One)
		StudentProfile p1 = new StudentProfile();
		p1.setBio("Alice is a top performer.");
		p1.setStudent(s1);

		StudentProfile p2 = new StudentProfile();
		p2.setBio("Bob loves sports.");
		p2.setStudent(s2);

		profileRepo.saveAll(Arrays.asList(p1, p2));

		// Create Teachers
		Teacher t1 = new Teacher();
		t1.setName("Mr. Smith");

		Teacher t2 = new Teacher();
		t2.setName("Ms. Johnson");

		teacherRepo.saveAll(Arrays.asList(t1, t2));

		// Create Courses (Many-to-Many with Teachers)
		Course math = new Course();
		math.setTitle("Mathematics");
		math.setTeachers(Arrays.asList(t1, t2));

		Course science = new Course();
		science.setTitle("Science");
		science.setTeachers(Arrays.asList(t1));

		courseRepo.saveAll(Arrays.asList(math, science));

		// Link back Teachers to Courses (for bidirectional consistency)
		t1.setCourses(Arrays.asList(math, science));
		t2.setCourses(Arrays.asList(math));
		teacherRepo.saveAll(Arrays.asList(t1, t2));

		// Create Enrollments (Many-to-Many with extra field)
		Enrollment e1 = new Enrollment();
		e1.setStudent(s1);
		e1.setCourse(math);
		e1.setGrade("A");

		Enrollment e2 = new Enrollment();
		e2.setStudent(s1);
		e2.setCourse(science);
		e2.setGrade("B");

		Enrollment e3 = new Enrollment();
		e3.setStudent(s2);
		e3.setCourse(math);
		e3.setGrade("A+");

		enrollmentRepo.saveAll(Arrays.asList(e1, e2, e3));

		// --------- FETCH & PRINT ----------
		studentRepo.findAllWithClassroom().forEach(st -> {
			System.out.println(st.getName());
			System.out.println(st.getClassroom().getName());
			System.out.println();
		});
	}
}