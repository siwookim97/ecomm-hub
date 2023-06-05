package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.dto.ProductDto;
import com.likelion.ecommhub.service.ImageService;
import com.likelion.ecommhub.service.MemberService;
import com.likelion.ecommhub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final MemberService memberService;
    private final ProductService productService;
    private final ImageService imageService;

    @GetMapping("/home")
    public String showProducts(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        Page<Product> pagingProducts = productService.findAllProducts(page);
        model.addAttribute("pagingProducts", pagingProducts);
        return "product/home";
    }

    @GetMapping("/enroll")
    public String showEnrollProductForm(Model model) {

        return "product/enroll";
    }

    @PostMapping("/enroll")
    public String enroll(@ModelAttribute("productDto") @Valid ProductDto productDto,
                         Principal principal) throws IOException {

        Member findMember = memberService.findMembrByUsername(principal.getName());
        Product enrolledProduct = productService.enroll(productDto, findMember);
        imageService.uploadImages(productDto.getImages(), enrolledProduct);

        return "redirect:/product/home";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products = productService.searchProduct(keyword);
        model.addAttribute("products", products);
        return "product/home";
    }
}