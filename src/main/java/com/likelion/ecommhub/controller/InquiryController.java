package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.dto.InquiryDto;
import com.likelion.ecommhub.service.InquiryService;
import com.likelion.ecommhub.service.MemberService;
import com.likelion.ecommhub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;
    private final MemberService memberService;
    private final ProductService productService;

    @GetMapping("/enroll")
    public String enrollInquiryForm(Model model) {
        return "inquiry/enroll";
    }

    @PostMapping("/enroll/{productId}")
    public String enrollInquiry(@ModelAttribute("inquiryDto") @Valid InquiryDto inquiryDto,
                                @PathVariable Long productId,
                                Principal principal) {

        Member findMember = memberService.findMembrByUsername(principal.getName());
        Product findProduct = productService.findProductById(productId);
        inquiryService.postInquiry(inquiryDto, findMember, findProduct);

        return "redirect:/product/home";
    }

    @PostMapping("/delete/{productId}")
    public String deleteInquiry(@PathVariable Long productId, Principal principal) {
        Member findMember = memberService.findMembrByUsername(principal.getName());
        inquiryService.deleteInquiry(productId, findMember);

        return "redirect:/product/home";
    }
}
