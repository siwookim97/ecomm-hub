package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.dto.ProductSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<Product> search(ProductSearchCondition condition, Pageable pageable);
}
