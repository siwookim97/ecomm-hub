package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.domain.ProductState;
import com.likelion.ecommhub.dto.ProductDto;
import com.likelion.ecommhub.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Transactional
    public Product enroll(ProductDto productDto) {

        Product product = saveProduct(productDto);

        return productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductId(long id){
        return productRepository.findById(id);

    }

    private Product saveProduct(ProductDto productDto) {

        ProductState productState = productDto.getInventory() == 0
            ? ProductState.SOLD_OUT : ProductState.ON_SALE;

        return Product.builder()
            .name(productDto.getName())
            .price(productDto.getPrice())
            .detail(productDto.getDetail())
            .inventory(productDto.getInventory())
            .productState(productState)
            .build();
    }
}
