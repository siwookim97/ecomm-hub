package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.dto.ProductDto;
import com.likelion.ecommhub.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    ProductRepository productRepository;

    @Transactional
    public String enroll(ProductDto productDto) {

        Product product = createProduct(productDto);
        productRepository.save(product);
        return "상품 등록 성공";
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    private Product createProduct(ProductDto productDto) {
        return new Product(
                productDto.getName(),
                productDto.getPrice(),
                productDto.getDetail(),
                productDto.getInventory()
        );
    }
}