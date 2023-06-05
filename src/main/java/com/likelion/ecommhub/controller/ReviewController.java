package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.dto.ReviewDto;
import com.likelion.ecommhub.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/enroll/{productId}")
    public String enrollReviewForm(@PathVariable Long productId, Model model) {
        model.addAttribute("productId", productId);

        return "review/enroll";
    }

    @PostMapping("/enroll/{productId}")
    public String enrollReview(@ModelAttribute("reviewDto") @Valid ReviewDto reviewDto,
                                @PathVariable Long productId,
                                Principal principal) {

        // TODO: 주문 테이블 검증로직 필요

        return "redirect:/product/home";
    }

    // TODO: 주문 테이블 추가 후 삭제 기능 작업
    @PostMapping("/delete/{productId}")
    public String deleteReview(@PathVariable Long productId, Principal principal) {

        return "redirect:/product/home";
    }
}
