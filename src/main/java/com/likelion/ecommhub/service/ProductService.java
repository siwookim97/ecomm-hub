package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.domain.ProductState;
import com.likelion.ecommhub.dto.ProductDto;
import com.likelion.ecommhub.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Page<Product> findAllProducts(int page) {
        Pageable pageable = PageRequest.of(page,12);
        return productRepository.findAll(pageable);
    }


    public Product findProductById(Long productId) {
        return productRepository.findById(productId).get();
    }

    public Optional<Product> getProductId(long id){
        return productRepository.findById(id);

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

    public Page<Product> searchProduct(String keyword, int page) {

        String normalizeKeyword = normalizeKeyword(keyword);

        List<Product> keywordList = productRepository.findAll()
                .stream()
                .filter(product -> product.getName().contains(normalizeKeyword))
                .collect(Collectors.toList());

        PageRequest pageRequest = PageRequest.of(page, 12);
        int start = (int)pageRequest.getOffset();
        int end = Math.min((start+pageRequest.getPageSize()),keywordList.size());

        Page<Product> keywordPage = new PageImpl<>(keywordList.subList(start,end),pageRequest, keywordList.size());

        return keywordPage;
    }

    private String normalizeKeyword(String keyword){
        String normalizeKeyword = keyword.trim();

        normalizeKeyword = normalizeKeyword.toLowerCase();

        return normalizeKeyword;
    }
}
