package com.example.spring_jpa_notes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Basic @Transactional
    @Transactional
    public Product createProduct(String name, int quantity, double price) {
        Product p = new Product();
        p.setName(name);
        p.setQuantity(quantity);
        p.setPrice(price);
        return productRepository.save(p);
    }

    // Transaction with specific isolation and propagation
    @Transactional(isolation = org.springframework.transaction.annotation.Isolation.REPEATABLE_READ,
            propagation = org.springframework.transaction.annotation.Propagation.REQUIRED)
    public void updatePrice(Long productId, double newPrice) {
        Product p = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        p.setPrice(newPrice);
    }

    // Optimistic Locking Example
    @Transactional
    public void updateQuantityOptimistic(Long productId, int newQuantity) {
        Product p = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        p.setQuantity(newQuantity);
        // version will be checked automatically on commit
    }

    // Pessimistic Locking Example
    @Transactional
    public void updateQuantityPessimistic(Long productId, int newQuantity) {
        Product p = productRepository.findByIdForUpdate(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        p.setQuantity(newQuantity);
    }
}
