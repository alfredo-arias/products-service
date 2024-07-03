package com.actividadgrupal.product_service.repository;

import com.actividadgrupal.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    List<Product> findAllByCategory(String category);
    List<Product> findAllByCategoryAndSize(String category, String size);
}
