package com.likelion.ecommhub.controller;

import static com.likelion.ecommhub.domain.MemberRole.ROLE_SELLER;

import com.likelion.ecommhub.config.auth.MemberDetails;
import com.likelion.ecommhub.domain.Cart;
import com.likelion.ecommhub.domain.CartItem;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.domain.Review;
import com.likelion.ecommhub.dto.ProductDto;
import com.likelion.ecommhub.dto.ProductSearchCondition;
import com.likelion.ecommhub.dto.ProductSearchResult;
import com.likelion.ecommhub.service.CartService;
import com.likelion.ecommhub.service.ImageService;
import com.likelion.ecommhub.service.MemberService;
import com.likelion.ecommhub.service.ProductService;
import com.likelion.ecommhub.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final CartService cartService;
    private final ReviewService reviewService;

    @GetMapping("/home")
    public String showProducts(Model model, ProductSearchCondition condition,
        @PageableDefault(size = 12) Pageable pageable) {

        Page<ProductSearchResult> pagingProducts = productService.search(condition, pageable);
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
        imageService.saveFile(productDto.getImage(), enrolledProduct);

        return "redirect:/product/home";
    }

    @GetMapping("/search")
    public String searchProduct(ProductSearchCondition condition, Model model,
        @PageableDefault(size = 12) Pageable pageable) {

        Page<ProductSearchResult> pagingProducts = productService.search(condition, pageable);
        model.addAttribute("condition", condition);
        model.addAttribute("pagingProducts", pagingProducts);

        return "product/home";
    }

  @GetMapping("/view/{productId}")
    public String ProductView(Model model, @PathVariable("productId") Long id,
        @AuthenticationPrincipal MemberDetails memberDetails) {

        if (memberDetails != null) {
            Member member = memberDetails.getMember();

            if (member.getMemberRole().equals(ROLE_SELLER)) {
                List<Review> reviews = reviewService.getReviewList(id);
                model.addAttribute("reviews", reviews);
                model.addAttribute("item", productService.findProductById(id));
                model.addAttribute("user", member);
            } else {
                Member findMember = memberService.getMemberById(member.getId());

                int cartCount = 0;
                Cart memberCart = cartService.findMemberCart(findMember.getId());
                List<CartItem> cartItems = cartService.MemberCartView(memberCart);

                for (CartItem cartItem : cartItems) {
                    cartCount += cartItem.getCount();
                }

                List<Review> reviews = reviewService.getReviewList(id);
                model.addAttribute("reviews", reviews);
                model.addAttribute("cartCount", cartCount);
                model.addAttribute("item", productService.findProductById(id));
                model.addAttribute("user", member);
            }
        } else {
            model.addAttribute("item", productService.findProductById(id));
        }

        return "/product/productView";
    }
}
