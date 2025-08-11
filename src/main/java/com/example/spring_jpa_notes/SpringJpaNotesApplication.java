package com.example.spring_jpa_notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@SpringBootApplication
public class SpringJpaNotesApplication  implements CommandLineRunner  {

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaNotesApplication.class, args);
	}

	@Autowired private StudentRepository studentRepo;

	@Override
	public void run(String... args) {
		// Sample Data
		studentRepo.save(new Student(null, "Alice", 20, "alice@gmail.com"));
		studentRepo.save(new Student(null, "Bob", 25, "bob@yahoo.com"));
		studentRepo.save(new Student(null, "Charlie", 30, "charlie@gmail.com"));
		studentRepo.save(new Student(null, "David", 28, "david@hotmail.com"));
		studentRepo.save(new Student(null, "Eve", 22, "eve@gmail.com"));

		// -------- Pagination --------
		Pageable pageReq = PageRequest.of(0, 2, Sort.by("age").ascending());
		Page<Student> pageResult = studentRepo.findAll(pageReq);
		System.out.println("\n[Pagination - Page 0, Size 2, Sorted by Age]");
		pageResult.forEach(s -> System.out.println(s.getName() + " - " + s.getAge()));

		// -------- Slice --------
		Slice<Student> sliceResult = studentRepo.findAll(PageRequest.of(0, 3));
		System.out.println("\n[Slice - First 3 Records]");
		sliceResult.forEach(s -> System.out.println(s.getName()));

		// -------- Sorting --------
		System.out.println("\n[Sorting - Descending by Name]");
		studentRepo.findAll(Sort.by(Sort.Direction.DESC, "name"))
				.forEach(s -> System.out.println(s.getName()));

		// -------- Specification (Dynamic Query) --------
		Specification<Student> spec = Specification
				.where(StudentSpecifications.ageGreaterThan(22))
				.and(StudentSpecifications.hasName("Bob"));

		System.out.println("\n[Specification - Age > 22 AND Name = 'Bob']");
		studentRepo.findAll(spec).forEach(s -> System.out.println(s.getName()));

		// -------- Example (Query by Example) --------
		Student probe = new Student();
		probe.setEmail("gmail.com"); // Partial match
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnorePaths("id", "age")
				.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		Example<Student> example = Example.of(probe, matcher);

		System.out.println("\n[Example - Email contains 'gmail.com']");
		studentRepo.findAll(example).forEach(s -> System.out.println(s.getName()));

		// -------- Named Query --------
		System.out.println("\n[Named Query - Students age >= 25]");
		studentRepo.findByMinAge(25).forEach(s -> System.out.println(s.getName()));

		// -------- Named Native Query --------
		System.out.println("\n[Named Native Query - Students with 'hotmail' domain]");
		studentRepo.findByDomainNative("hotmail").forEach(s -> System.out.println(s.getName()));
	}
}