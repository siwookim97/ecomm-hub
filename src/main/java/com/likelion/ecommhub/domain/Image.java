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
    public Image(String originFilename, String storeFilename) {
        this.originFilename = originFilename;
        this.storeFilename = storeFilename;
    }

    public void setProduct(Product product) {
        if (this.product != null) {
            this.product.getImages().remove(this);
        }
        this.product = product;
        product.getImages().add(this);
    }
}