package com.likelion.ecommhub.dto;

import com.likelion.ecommhub.domain.Image;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductSearchResult {

    private Long productId;
    private String productName;
    private String detail;
    private int price;
    private int inventory;
    private List<Image> images;
    private Long sellerId;
    private String sellerName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedDate;

    @QueryProjection
    public ProductSearchResult(Long productId, String productName, String detail,
                               int price, int inventory,
                               List<Image> images,
        Long sellerId, String sellerName, LocalDateTime modifiedDate) {
        this.productId = productId;
        this.productName = productName;
        this.detail = detail;
        this.price = price;
        this.inventory = inventory;
        this.images = images;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.modifiedDate = modifiedDate;
    }
}
