package com.likelion.ecommhub.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bucketObjectUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Image(String bucketObjectUrl) {
        this.bucketObjectUrl = bucketObjectUrl;
    }

    public void insertIntoProduct(Product product) {
        this.product = product;
        product.insertImage(this);
    }
}
