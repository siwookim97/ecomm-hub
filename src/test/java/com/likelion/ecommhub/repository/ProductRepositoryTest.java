package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.domain.ProductState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void init() {
        Product productA = new Product(1L, "apple", 1000, "this is apple", ProductState.ON_SALE, 100);
        Product productB = new Product(2L, "banana", 3000, "this is banana", ProductState.ON_SALE, 50);
        Product productC = new Product(3L, "coke", 500, "this is coke", ProductState.ON_SALE, 300);

        productRepository.save(productA);
        productRepository.save(productB);
        productRepository.save(productC);
    }

    @Test
    @DisplayName("Member 엔티티 save 확인 테스트")
    void productInsertTest() {
        int count_product = productRepository.findAll().size();

        assertThat(count_product).isEqualTo(3);
    }
}