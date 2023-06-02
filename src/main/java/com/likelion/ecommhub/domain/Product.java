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

    @Builder
    public Product(String name, int price, String detail, int inventory,
                   ProductState productState) {
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.inventory = inventory;
        this.productState = productState;
    }

    public void updateProductSoldOut() {
        if(inventory == 0){
            productState = ProductState.SOLD_OUT;
        }
    }
}