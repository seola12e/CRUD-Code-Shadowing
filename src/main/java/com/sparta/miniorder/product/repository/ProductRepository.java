package com.sparta.miniorder.product.repository;

import com.sparta.miniorder.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
