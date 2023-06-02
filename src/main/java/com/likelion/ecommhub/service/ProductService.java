package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Image;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.domain.ProductState;
import com.likelion.ecommhub.dto.ProductDto;
import com.likelion.ecommhub.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Transactional
    public String enroll(ProductDto productDto, List<Image> images) {

        Product product = createProduct(productDto, images);

        productRepository.save(product);
        return "상품 등록 성공";
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    private Product createProduct(ProductDto productDto, List<Image> images) {

        ProductState productState = productDto.getInventory() == 0
                ? ProductState.SOLD_OUT : ProductState.ON_SALE;

        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .detail(productDto.getDetail())
                .inventory(productDto.getInventory())
                .productState(productState)
                .images(images)
                .build();

        return product;
    }
}