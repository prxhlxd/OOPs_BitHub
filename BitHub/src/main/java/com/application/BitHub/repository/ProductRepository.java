package com.application.BitHub.repository;

import java.util.List;
import java.util.Optional;
import com.application.BitHub.objects.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findByName(String name);

    public List<Product> findByNameContainingIgnoreCase(String name);

    public Optional<Product> findById(Long id);

    public List<Product> findBySeller(Long seller);

}

