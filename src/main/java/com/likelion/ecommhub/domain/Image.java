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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Image(String bucketObjectUrl) {
        this.bucketObjectUrl = bucketObjectUrl;
    }

    public void setProduct(Product product) {
        if (this.product != null) {
            this.product.getImages().remove(this);
        }
        this.product = product;
        product.getImages().add(this);
    }
}
