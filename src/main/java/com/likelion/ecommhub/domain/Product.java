package com.likelion.ecommhub.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING)
    private ProductState productState;

    @Column(nullable = false)
    private int inventory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<CartItem> cartItems = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Inquiry> inquiries = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();


    @Builder
    public Product(String name, int price, String detail, int inventory,
        ProductState productState) {
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.inventory = inventory;
        this.productState = productState;
    }

    public Product(Long id, String name, int price, String detail, int inventory,
        ProductState productState, List<CartItem> cartItems) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.inventory = inventory;
        this.productState = productState;
        this.cartItems = cartItems;
    }

    public void updateProductSoldOut() {
        if (inventory == 0) {
            productState = ProductState.SOLD_OUT;
        }
    }

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getProducts().remove(this);
        }
        this.member = member;
        member.getProducts().add(this);
    }

    public void increaseInventory(int amount) {
        this.inventory += amount;
        updateProductSoldOut();
    }

    public void decreaseInventory(int amount) {
        if (this.inventory < amount) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.inventory -= amount;
        updateProductSoldOut();
    }


}