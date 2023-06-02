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
        Product productA = new Product("apple", 1000, "this is apple",  100, ProductState.ON_SALE);
        Product productB = new Product( "banana", 3000, "this is banana",  50, ProductState.ON_SALE);
        Product productC = new Product( "coke", 500, "this is coke",  300, ProductState.ON_SALE);

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