package com.solovev.kiteshop.repository;

import com.solovev.kiteshop.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
