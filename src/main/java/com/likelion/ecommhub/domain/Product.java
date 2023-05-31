package com.likelion.ecommhub.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    @ColumnDefault("ON_SALE")
    private ProductState productState;
    @Column(nullable = false)
    private int inventory;

    public Product( String name, int price, String detail, int inventory) {
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.inventory = inventory;
    }

    public void setProductState() {
        if(inventory == 0){
            productState = ProductState.SOLD_OUT;
        }
    }
}