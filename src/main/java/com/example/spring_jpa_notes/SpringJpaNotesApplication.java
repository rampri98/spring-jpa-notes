package com.example.spring_jpa_notes;

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

	@Autowired
	private ProductService productService;

	@Override
	public void run(String... args) {
		// Create product
		Product p = productService.createProduct("Laptop", 10, 1200.00);
		System.out.println("Created Product ID: " + p.getId());

		// Update price with specific isolation level
		productService.updatePrice(p.getId(), 1300.00);
		System.out.println("Updated price to 1300");

		// Optimistic lock update
		productService.updateQuantityOptimistic(p.getId(), 8);
		System.out.println("Updated quantity with optimistic locking");

		// Pessimistic lock update
		productService.updateQuantityPessimistic(p.getId(), 5);
		System.out.println("Updated quantity with pessimistic locking");
	}
}