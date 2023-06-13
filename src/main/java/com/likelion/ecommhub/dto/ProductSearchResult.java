package com.likelion.ecommhub.dto;

import com.likelion.ecommhub.domain.Image;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ProductSearchResult {

    private Long productId;
    private String productName;
    private String detail;
    private int price;
    private int inventory;
    private Image image;
    private Long sellerId;
    private String sellerName;
    private LocalDateTime modifiedDate;

    @QueryProjection
    public ProductSearchResult(Long productId, String productName, String detail,
                               int price, int inventory, Image image,
                               Long sellerId, String sellerName, LocalDateTime modifiedDate) {
        this.productId = productId;
        this.productName = productName;
        this.detail = detail;
        this.price = price;
        this.inventory = inventory;
        this.image = image;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.modifiedDate = modifiedDate;
    }
}
