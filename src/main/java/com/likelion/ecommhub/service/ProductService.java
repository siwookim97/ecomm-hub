package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.domain.ProductState;
import com.likelion.ecommhub.dto.ProductDto;
import com.likelion.ecommhub.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Transactional
    public Product enroll(ProductDto productDto, Member member) {

        Product product = saveProduct(productDto, member);

        return productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long productId) {
        return productRepository.findById(productId).get();
    }

    private Product saveProduct(ProductDto productDto, Member member) {

        ProductState productState = productDto.getInventory() == 0
                ? ProductState.SOLD_OUT : ProductState.ON_SALE;

        Product createdProduct = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .detail(productDto.getDetail())
                .inventory(productDto.getInventory())
                .productState(productState)
                .build();
        createdProduct.setMember(member);

        return createdProduct;
    }
}
