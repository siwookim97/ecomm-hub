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

    private String originFilename;
    private String storeFilename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Image(String originFilename, String storeFilename, Product product) {
        this.originFilename = originFilename;
        this.storeFilename = storeFilename;
        this.product = product;
    }

    public void setProduct(Product product) {
        if (this.product != null) {
            this.product = product;
        }
        product.getImages().add(this);
    }
}
