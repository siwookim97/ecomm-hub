package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.CartItem;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Order;
import com.likelion.ecommhub.domain.OrderItem;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.domain.Sales;
import com.likelion.ecommhub.repository.OrderItemRepository;
import com.likelion.ecommhub.repository.OrderRepository;
import com.likelion.ecommhub.repository.SalesRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberService memberService;
    private final ProductService productService;
    private final SalesRepository salesRepository;

    // 회원가입 하면 회원 당 주문 하나 생성
    @Transactional
    public void createOrder(Member member) {
        Order order = Order.createOrder(member);
        orderRepository.save(order);
    }

    // id에 해당하는 주문아이템 찾기
    public List<OrderItem> findUserOrderItems(Long memberId) {
        return orderItemRepository.findOrderItemsByMemberId(memberId);
    }

    // OrderItem 하나 찾기
    public OrderItem findOrderitem(Long orderItemId) {
        return orderItemRepository.findOrderItemById(orderItemId);
    }

    // 장바구니상품주문
    @Transactional
    public OrderItem addCartOrder(Long productId, Long memberId, CartItem cartItem) {
        Member member = memberService.getMemberById(memberId);
        OrderItem orderItem = OrderItem.createOrderItem(productId, member, cartItem);
        orderItemRepository.save(orderItem);

        return orderItem;
    }

    // 주문하면 Order 만들기
    @Transactional
    public Order addOrder(Member member, List<OrderItem> orderItemList) {
        Order userOrder = Order.createOrder(member, orderItemList);
        orderRepository.save(userOrder);

        return userOrder;
    }

    // 주문 취소 기능
    @Transactional
    public void orderCancel(OrderItem cancelItem) {
        Product product = productService.findProductById(cancelItem.getProductId());
        // 해당 item 재고 다시 증가
        product.increaseInventory(cancelItem.getProductCount());
        // 해당 orderItem의 주문 상태 1로 변경 -> 주문 취소를 의미
        cancelItem.setIsCancel(cancelItem.getIsCancel() + 1);
        orderItemRepository.save(cancelItem);
    }

    //매출액 증가
    @Transactional
    public void increaseSales(Long productId, BigDecimal amount,Order order) {
        int month = LocalDateTime.now().getMonthValue();
        int year = LocalDateTime.now().getYear();

        Sales salesEntry = Sales.builder()
            .sales(amount)
            .saleDate(LocalDateTime.now().withMonth(month))
            .saleYear(year)
            .product(productService.findProductById(productId))
            .member(productService.findProductById(productId).getMember())
            .build();
        salesEntry.setOrder(order);

        salesRepository.save(salesEntry);
    }
}