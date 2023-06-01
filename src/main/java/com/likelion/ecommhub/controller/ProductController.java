package com.likelion.ecommhub.controller;


import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.dto.ProductDto;
import com.likelion.ecommhub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/home")
    public String showProducts(Model model) {
        List<Product> productList = productService.findAllProducts();
        model.addAttribute("products", productList);
        return "product/home";
    }

    @GetMapping("/enroll")
    public String showEnrollProductForm(Model model) {
        model.addAttribute("ProductDto", new ProductDto());
        return "product/enroll";
    }

    @PostMapping("/enroll")
    public String enroll(@ModelAttribute("ProductDto") @Valid ProductDto productDto) {
        String result = productService.enroll(productDto);
        System.out.println("result = " + result);
        return "redirect:/product/home";
    }
}