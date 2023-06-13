package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.config.auth.MemberDetails;
import com.likelion.ecommhub.domain.Cart;
import com.likelion.ecommhub.domain.CartItem;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.OrderItem;
import com.likelion.ecommhub.service.CartService;
import com.likelion.ecommhub.service.MemberService;
import com.likelion.ecommhub.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final CartService cartService;
    private final OrderService orderService;

    @PreAuthorize("hasRole('ROLE_BUYER')")
    @GetMapping("/usr/member/orderList")
    public String orderList(@AuthenticationPrincipal MemberDetails memberDetails, Model model) {
        Long id = memberDetails.getMember().getId();

        List<OrderItem> orderItemList = orderService.findUserOrderItems(id);

        int totalCount = 0;
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getIsCancel() != 1) {
                totalCount += orderItem.getProductCount();
            }
        }

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("orderItems", orderItemList);
        model.addAttribute("user", memberService.getMemberId(id));

        return "usr/member/orderList";
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_BUYER')")
    @PostMapping("/usr/member/cart/checkout")
    public String cartCheckout(@AuthenticationPrincipal MemberDetails memberDetails, Model model) {

        Long id = memberDetails.getMember().getId();
        Member findMember = memberService.getMemberById(id);
        Cart userCart = cartService.findMemberCart(findMember.getId());
        List<CartItem> userCartItems = cartService.MemberCartView(userCart);

        int totalPrice = 0;
        for (CartItem cartItem : userCartItems) {
            if (cartItem.getProduct().getInventory() == 0
                || cartItem.getProduct().getInventory() < cartItem.getCount()) {
                return "redirect:/product/home";
            }
            totalPrice += cartItem.getCount() * cartItem.getProduct().getPrice();
        }

        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartItem cartItem : userCartItems) {
            cartItem.getProduct().decreaseInventory(cartItem.getCount());
            OrderItem orderItem = orderService.addCartOrder(cartItem.getProduct().getId(),
                findMember.getId(), cartItem);
            orderItemList.add(orderItem);
        }

        orderService.addOrder(findMember, orderItemList);

        cartService.cartDelete(id);

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartItems", userCartItems);
        model.addAttribute("user", findMember);

        return "redirect:/usr/member/cart";
    }

    @PreAuthorize("hasRole('ROLE_BUYER')")
    @PostMapping("/usr/member/checkout/cancel/{orderItemId}")
    public String cancelOrder(@PathVariable("orderItemId") Long orderItemId, Model model,
        @AuthenticationPrincipal MemberDetails memberDetails) {

        Long id = memberDetails.getMember().getId();
        OrderItem cancelItem = orderService.findOrderitem(orderItemId);

        List<OrderItem> orderItemList = orderService.findUserOrderItems(id);
        int totalCount = 0;
        for (OrderItem orderItem : orderItemList) {
            totalCount += orderItem.getProductCount();
        }
        totalCount = totalCount - cancelItem.getProductCount();

        orderService.orderCancel(cancelItem);

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("orderItems", orderItemList);

        return "redirect:/usr/member/orderList";
    }
}
