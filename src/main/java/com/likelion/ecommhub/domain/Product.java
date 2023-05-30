package com.likelion.ecommhub.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public Product(Long id, String name, int price, String detail, ProductState productState, int inventory) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.productState = productState;
        this.inventory = inventory;
    }
}
