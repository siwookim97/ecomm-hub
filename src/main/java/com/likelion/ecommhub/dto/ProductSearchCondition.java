package com.likelion.ecommhub.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchCondition {

    private String keyword;
    private String sortCode;
}
