package com.likelion.ecommhub.dto;

import com.likelion.ecommhub.domain.Image;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductSearchResult {

    private Long productId;
    private String productName;
    private int price;
    private int inventory;
    private List<Image> images;
    private Long sellerId;
    private String sellerName;

    @QueryProjection

    public ProductSearchResult(Long productId, String productName, int price,
                               int inventory, List<Image> images, Long sellerId,
                               String sellerName) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.inventory = inventory;
        this.images = images;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
    }
}
