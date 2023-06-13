package com.likelion.ecommhub.controller;

import java.util.List;
import java.util.Optional;

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

    // 내 장바구니 조회
    // {memberid} pathvariable 삭제 -> @PreAuth``` ->
    @PreAuthorize("hasRole('ROLE_BUYER')")
    @GetMapping("/cart")
    public String myCartPage(Model model,
                             @AuthenticationPrincipal MemberDetails memberDetails) {

        Member findMember = memberService.getMemberById(memberDetails.getMember().getId());

        // 있는지 없는지 확인
        if (memberDetails != null) {
            // User의 장바구니를 가져온다.
            Cart cart = memberDetails.getMember().getCart();
            // 장바구니의 아이템을 가져온다.

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

    //특정 상품 장바구니에 추가
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

    //특정 상품 장바구니에서 삭제
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