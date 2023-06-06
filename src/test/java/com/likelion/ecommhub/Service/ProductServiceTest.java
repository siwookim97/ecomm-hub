package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.dto.ProductDto;
import com.likelion.ecommhub.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;


    @Test
    @DisplayName("상품 등록 테스트")
    void productEnrollTest() {
        // when
        productService.enroll(new ProductDto("바나나", 100, "바나나입니다", 500, null));

        // then
        List<Product> allProducts = productService.findAllProducts();
        Assertions.assertThat(allProducts.size()).isEqualTo(3);
    }
}