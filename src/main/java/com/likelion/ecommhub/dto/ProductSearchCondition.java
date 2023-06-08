package com.likelion.ecommhub.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductSearchCondition {

    private String productName;
    private String sellerName;
    private String productState;
}
