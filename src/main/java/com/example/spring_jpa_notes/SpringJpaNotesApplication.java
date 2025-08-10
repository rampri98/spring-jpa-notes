package com.example.spring_jpa_notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringJpaNotesApplication  implements CommandLineRunner  {
	@Autowired
	private EmployeeRepo employeeRepo;

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaNotesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Employee e1 = new Employee();
		e1.setName("Akash");
		e1.setEmail("ak@mail.com");
		employeeRepo.save(e1);

		Employee e2 = new Employee();
		e2.setName("Ramya");
		e2.setEmail("rp@mail.com");
		employeeRepo.save(e2);

		Employee e3 = new Employee();
		e3.setName("Grace");
		e3.setEmail("gc@mail.com");
		employeeRepo.save(e3);

		// Fetch all
		List<Employee> emps = employeeRepo.findAll();
		emps.forEach(e -> System.out.println(e.getName()));
	}
}