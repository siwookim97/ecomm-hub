package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.config.auth.MemberDetails;
import com.likelion.ecommhub.domain.Inquiry;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.dto.InquiryDto;
import com.likelion.ecommhub.service.InquiryService;
import com.likelion.ecommhub.service.MemberService;
import com.likelion.ecommhub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;
    private final MemberService memberService;
    private final ProductService productService;

    @GetMapping("/enroll/{productId}")
    public String enrollInquiryForm(@PathVariable Long productId, Model model) {
        model.addAttribute("productId", productId);

        return "inquiry/enroll";
    }

    @PostMapping("/enroll/{productId}")
    public String enrollInquiry(@ModelAttribute("inquiryDto") @Valid InquiryDto inquiryDto,
                                @PathVariable Long productId,
                                Principal principal) {

        Member findMember = memberService.findMemberByUsername(principal.getName());
        Product findProduct = productService.findProductById(productId);
        inquiryService.postInquiry(inquiryDto, findMember, findProduct);

        return "redirect:/product/view/" + productId;
    }

    @GetMapping("/view/{productId}")
    public String printInquiry(Model model,
                               @PathVariable Long productId,
                               @AuthenticationPrincipal MemberDetails memberDetails) {
        List<Inquiry> findInquiries = inquiryService.printInquiry(productId);
        Product findProduct = productService.findProductById(productId);

        System.out.println("count : " + findInquiries.size());
        for (Inquiry findInquiry : findInquiries) {
            System.out.println("findInquiry.getTitle() = " + findInquiry.getTitle());
            System.out.println("findInquiry.getContent() = " + findInquiry.getContent());
        }
        model.addAttribute("inquiries", findInquiries);
        model.addAttribute("prodcut", findProduct);

        return "inquiry/view";
    }

    @PostMapping("/delete/{productId}")
    public String deleteInquiry(@PathVariable Long productId, Principal principal) {
        Member findMember = memberService.findMemberByUsername(principal.getName());
        inquiryService.deleteInquiry(productId, findMember);

        return "redirect:/product/home";
    }
}
