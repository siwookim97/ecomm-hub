package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.config.auth.MemberDetails;
import com.likelion.ecommhub.dto.ReviewDto;
import com.likelion.ecommhub.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String enrollReview(@ModelAttribute @Valid ReviewDto reviewDto,
                               @PathVariable Long productId,
                               @AuthenticationPrincipal MemberDetails memberDetails) {

        reviewService.postReview(reviewDto, memberDetails.getMember().getId(), productId);

        return "redirect:/product/home";
    }

    @PostMapping("/delete/{productId}")
    public String deleteReview(@PathVariable Long productId,
                               @AuthenticationPrincipal MemberDetails memberDetails) {

        reviewService.deleteReview(productId, memberDetails.getMember().getId());

        return "redirect:/product/home";
    }
}
