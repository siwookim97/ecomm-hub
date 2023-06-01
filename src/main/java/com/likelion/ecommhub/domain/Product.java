package com.likelion.ecommhub.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
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
    public Product( String name, int price, String detail, int inventory) {
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.inventory = inventory;
    }

    public Product( String name, int price, String detail, int inventory, ProductState productState) {
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.inventory = inventory;
        this.productState = productState;
    }

    public void setProductState() {
        if(inventory == 0){
            productState = ProductState.SOLD_OUT;
        }
    }
}