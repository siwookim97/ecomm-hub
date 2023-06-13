package com.likelion.ecommhub.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchCondition {

    private String productName;
    private String sellerName;
    private String productState;
    private int sortCode;
}
