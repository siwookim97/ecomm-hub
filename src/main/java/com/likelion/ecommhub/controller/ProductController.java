package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.dto.ProductDto;
import com.likelion.ecommhub.dto.ProductSearchCondition;
import com.likelion.ecommhub.service.ImageService;
import com.likelion.ecommhub.service.MemberService;
import com.likelion.ecommhub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final MemberService memberService;
    private final ProductService productService;
    private final ImageService imageService;

    @GetMapping("/home")
    public String showProducts(Model model,
                               @PageableDefault(size = 12, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Product> pagingProducts = productService.findAllProducts(pageable);
        System.out.println("pagingProducts = " + pagingProducts);
        System.out.println("pagingProducts.getSize() = " + pagingProducts.getSize());
        System.out.println("pagingProducts.getTotalPages() = " + pagingProducts.getTotalPages());
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

        Member findMember = memberService.findMemberByUsername(principal.getName());
        Product enrolledProduct = productService.enroll(productDto, findMember);
        imageService.uploadImages(productDto.getImages(), enrolledProduct);

        return "redirect:/product/home";
    }

    @GetMapping("/search")
    public String searchProduct(ProductSearchCondition condition, Model model,
                                @PageableDefault(size = 12) Pageable pageable) {
        Page<Product> pagingProducts = productService.search(condition, pageable);
        System.out.println("pagingProducts = " + pagingProducts);
        System.out.println("pagingProducts.getSize() = " + pagingProducts.getSize());
        System.out.println("pagingProducts.getTotalPages() = " + pagingProducts.getTotalPages());
        model.addAttribute("condition", condition);
        model.addAttribute("pagingProducts", pagingProducts);

        return "product/home";
    }
}