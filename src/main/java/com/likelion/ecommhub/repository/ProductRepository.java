package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long>, ProductRepositoryCustom {

    Page<Product> findAll(Pageable pageable);
}
