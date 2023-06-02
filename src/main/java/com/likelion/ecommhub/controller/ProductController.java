package com.likelion.ecommhub.controller;


import com.likelion.ecommhub.domain.Image;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.dto.ProductDto;
import com.likelion.ecommhub.service.ImageService;
import com.likelion.ecommhub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        model.addAttribute("productDto", new ProductDto());

        return "product/enroll";
    }

    @PostMapping("/enroll")
    public String enroll(
            @RequestPart("images") List<MultipartFile> files,
            @ModelAttribute("productDto") @Valid ProductDto productDto) throws IOException {

        List<Image> images = imageService.uploadImages(files);
        String result = productService.enroll(productDto, images);
        System.out.println("result = " + result);

        return "redirect:/product/home";
    }
}