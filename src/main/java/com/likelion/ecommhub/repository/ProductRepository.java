package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,Long> {
}
