package com.likelion.ecommhub.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.likelion.ecommhub.config.auth.MemberDetails;
import com.likelion.ecommhub.domain.Cart;
import com.likelion.ecommhub.domain.CartItem;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.service.CartService;
import com.likelion.ecommhub.service.MemberService;
import com.likelion.ecommhub.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@Transactional
@RequiredArgsConstructor
@RequestMapping("/usr/member")
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;
    private final ProductService productService;

    @PreAuthorize("hasRole('ROLE_BUYER')")
    @GetMapping("/cart")
    public String myCartPage(Model model,
                             @AuthenticationPrincipal MemberDetails memberDetails) {

        Member findMember = memberService.getMemberById(memberDetails.getMember().getId());

        if (memberDetails != null) {
            Cart cart = memberDetails.getMember().getCart();
            List<CartItem> cartItems = cartService.MemberCartView(cart);

            int totalPrice = 0;
            for (CartItem cartItem : cartItems) {
                totalPrice += (cartItem.getProduct().getPrice() * cartItem.getCount());
            }

            model.addAttribute("cartItemList", cartItems);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("user", findMember);

            return "usr/member/cart";
        } else {
            return "redirect:/main";
        }
    }

    @PreAuthorize("hasRole('ROLE_BUYER')")
    @PostMapping("/cart/{productId}")
    public String myCartAdd(@PathVariable("productId") Long productId,
                            @RequestParam("amount") int count,
                            @AuthenticationPrincipal MemberDetails memberDetails) {

        Member member = memberService.getMemberById(memberDetails.getMember().getId());
        Product product = productService.getProductById(productId);

        cartService.addCart(member, product, count);

        return "redirect:/usr/member/cart";
    }

    @PreAuthorize("hasRole('ROLE_BUYER')")
    @GetMapping("/cart/{cartItemid}/delete")
    public String myCartDelete(@PathVariable("cartItemid") Long cartItemid,
                               @AuthenticationPrincipal MemberDetails memberDetails) {

        Member member = memberService.getMemberById(memberDetails.getMember().getId());

        if(member != null) {
            cartService.cartItemDelete(cartItemid);
        }

        return "redirect:/usr/member/cart";
    }
}
