package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.dto.ProductSearchCondition;
import com.likelion.ecommhub.dto.ProductSearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<ProductSearchResult> search(ProductSearchCondition condition, Pageable pageable);
}
