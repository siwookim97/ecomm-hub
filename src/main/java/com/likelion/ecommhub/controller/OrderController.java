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

    @GetMapping("/member/orderList/{id}")
    public String orderList(@PathVariable("id") Long id,
        @AuthenticationPrincipal MemberDetails memberDetails, Model model) {
        if (memberDetails.getMember().getId() == id) {

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

            return "orderList";
        } else {
            return "redirect:/main";
        }
    }

    @Transactional
    @PostMapping("/member/cart/checkout/{id}")
    public String cartCheckout(@PathVariable("id") Long id,
        @AuthenticationPrincipal MemberDetails memberDetails, Model model) {
        if (memberDetails.getMember().getId() == id) {
            Member member = memberService.getMemberId(id).orElseThrow();

            Cart userCart = cartService.findMemberCart(member.getId());

            List<CartItem> userCartItems = cartService.MemberCartView(userCart);

            int totalPrice = 0;
            for (CartItem cartItem : userCartItems) {
                if (cartItem.getProduct().getInventory() == 0
                    || cartItem.getProduct().getInventory() < cartItem.getCount()) {
                    return "redirect:/main";
                }
                totalPrice += cartItem.getCount() * cartItem.getProduct().getPrice();
            }

            List<OrderItem> orderItemList = new ArrayList<>();

            for (CartItem cartItem : userCartItems) {
                cartItem.getProduct().decreaseInventory(cartItem.getCount());

                OrderItem orderItem = orderService.addCartOrder(cartItem.getProduct().getId(),
                    member.getId(), cartItem);

                orderItemList.add(orderItem);
            }

            orderService.addOrder(member, orderItemList);

            cartService.cartDelete(id);

            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("cartItems", userCartItems);
            model.addAttribute("user", memberService.getMemberId(id));

            return "redirect:/member/{memberid}/cart";
        } else {
            return "redirect:/main";
        }
    }

    @PostMapping("/member/{id}/checkout/cancel/{orderItemId}")
    public String cancelOrder(@PathVariable("id") Long id,
        @PathVariable("orderItemId") Long orderItemId, Model model,
        @AuthenticationPrincipal MemberDetails memberDetails) {
        if (memberDetails.getMember().getId() == id) {
            OrderItem cancelItem = orderService.findOrderitem(orderItemId);
            Member member = memberService.getMemberId(id).orElseThrow();

            List<OrderItem> orderItemList = orderService.findUserOrderItems(id);
            int totalCount = 0;
            for (OrderItem orderItem : orderItemList) {
                totalCount += orderItem.getProductCount();
            }
            totalCount = totalCount - cancelItem.getProductCount();

            orderService.orderCancel(cancelItem);

            model.addAttribute("totalCount", totalCount);
            model.addAttribute("orderItems", orderItemList);

            return "redirect:/member/orderList/{id}";

        } else {
            return "redirect:/main";
        }
    }

}
