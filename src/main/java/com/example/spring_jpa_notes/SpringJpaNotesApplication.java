package com.example.spring_jpa_notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringJpaNotesApplication  implements CommandLineRunner  {
	@Autowired
	private UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaNotesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Create Address object
		Address address = new Address();
		address.setStreet("123 Spring Street");
		address.setCity("Hyderabad");
		address.setState("Telangana");
		address.setZipCode("500001");

		// Create User object
		User user = new User();
		user.setName("Ramya Priyadarshini");
		user.setEmail("ramya@example.com");
		user.setAgeInYears(26); // Won’t be stored in DB (Transient)
		user.setAddress(address);

		// Save to DB
		userRepo.save(user);

		// Fetch and print
		userRepo.findAll().forEach(u -> {
			System.out.println("User: " + u.getName() + ", Email: " + u.getEmail());
			System.out.println("City: " + u.getAddress().getCity());
		});
	}
}