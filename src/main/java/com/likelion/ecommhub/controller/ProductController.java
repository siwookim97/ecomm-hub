package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.dto.ProductDto;
import com.likelion.ecommhub.service.ImageService;
import com.likelion.ecommhub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ImageService imageService;

    @GetMapping("/home")
    public String showProducts(Model model) {
        List<Product> productList = productService.findAllProducts();
        model.addAttribute("products", productList);

        return "product/home";
    }

    @GetMapping("/enroll")
    public String showEnrollProductForm(Model model) {

        return "product/enroll";
    }

    @PostMapping("/enroll")
    public String enroll(@ModelAttribute("productDto") @Valid ProductDto productDto) throws IOException {

        Product enrolledProduct = productService.enroll(productDto);
        imageService.uploadImages(productDto.getImages(), enrolledProduct);

        return "redirect:/product/home";
    }
}