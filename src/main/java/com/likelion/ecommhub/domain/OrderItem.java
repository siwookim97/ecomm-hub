package com.likelion.ecommhub.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 구매자

    private Long productId; // 주문 상품 번호
    private String productName; // 주문 상품 이름
    private int productPrice; // 주문 상품 가격
    private int productCount; // 주문 상품 수량
    private int productTotalPrice; // 가격*수량

    private int isCancel; // 주문 취소 여부 (0:주문완료 / 1:주문취소)

    public void setIsCancel(int isCancel) {
        this.isCancel = isCancel;
    }

    @Builder
    public OrderItem(Long id, Order order, Member member, Long productId, String productName,
        int productPrice, int productCount, int productTotalPrice, int isCancel) {
        this.id = id;
        this.order = order;
        this.member = member;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCount = productCount;
        this.productTotalPrice = productTotalPrice;
        this.isCancel = isCancel;
    }

    // 장바구니 전체 주문
    public static OrderItem createOrderItem(Long productId, Member member, CartItem cartItem) {
        return OrderItem.builder()
            .productId(productId)
            .member(member)
            .productName(cartItem.getProduct().getName())
            .productPrice(cartItem.getProduct().getPrice())
            .productCount(cartItem.getCount())
            .productTotalPrice(cartItem.getProduct().getPrice() * cartItem.getCount())
            .build();
    }
    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getOrderItems().remove(this);
        }
        this.member = member;
        member.getOrderItems().add(this);
    }
    public void setOrder(Order order) {
        if (this.order != null) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }


}
