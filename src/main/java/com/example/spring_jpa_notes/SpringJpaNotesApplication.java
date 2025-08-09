package com.example.spring_jpa_notes;

import com.example.spring_jpa_notes.User;
import com.example.spring_jpa_notes.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringJpaNotesApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaNotesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Insert sample data
		userRepository.save(new User(0, "Akash", "ak@example.com"));
		userRepository.save(new User(1, "Ramya", "rp@example.com"));
		userRepository.save(new User(2, "Grace", "gc@example.com"));

		// Fetch all
		List<User> users = userRepository.findAll();
		users.forEach(u -> System.out.println(u.getName()));

		// Transaction example
		try {
			userRepository.updateEmailTransactional(1, "error@fail.com");
		} catch (Exception e) {
			System.out.println("Rollback triggered: " + e.getMessage());
		}
	}
}