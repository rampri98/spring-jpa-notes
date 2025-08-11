package com.example.spring_jpa_notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootApplication
public class SpringJpaNotesApplication  implements CommandLineRunner  {

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaNotesApplication.class, args);
	}

	@Autowired private StudentRepository studentRepo;

	@Override
	public void run(String... args) {
		// Create sample students
		studentRepo.save(new Student(null, "Alice", 20, "alice@example.com"));
		studentRepo.save(new Student(null, "Bob", 25, "bob@gmail.com"));
		studentRepo.save(new Student(null, "Charlie", 30, "charlie@yahoo.com"));

		// -------- PagingAndSortingRepository --------
		System.out.println("\n[PagingAndSortingRepository] First 2 students sorted by age:");
		studentRepo.findAll(PageRequest.of(0, 2, Sort.by("age").descending()))
				.forEach(s -> System.out.println(s.getName() + " - " + s.getAge()));

		// -------- Derived Queries --------
		System.out.println("\n[JpaRepository] Students with 'gmail' in email:");
		studentRepo.findByEmailContainingIgnoreCase("gmail")
				.forEach(s -> System.out.println(s.getName()));

		// -------- JPQL Query --------
		System.out.println("\n[JPQL] Students older than 24:");
		studentRepo.getStudentsOlderThan(24).forEach(s -> System.out.println(s.getName()));

		// -------- Native Query --------
		System.out.println("\n[Native] Students with 'yahoo' domain:");
		studentRepo.searchByEmailDomain("yahoo").forEach(s -> System.out.println(s.getName()));

		// -------- Modifying Queries --------
		System.out.println("\n[Modifying] Updating email for Alice...");
		studentRepo.updateEmailById(1L, "alice@newmail.com");

		System.out.println("[Modifying] Deleting students younger than 21...");
		studentRepo.deleteStudentsYoungerThan(21);

		System.out.println("\n[Final List] Remaining Students:");
		studentRepo.findAll().forEach(s -> System.out.println(s.getName() + " - " + s.getEmail()));
	}
}