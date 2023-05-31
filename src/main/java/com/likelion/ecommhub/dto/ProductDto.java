package com.likelion.ecommhub.dto;

import lombok.Getter;

import javax.validation.constraints.*;

@Getter
public class ProductDto {

    @NotEmpty(message = "상품 이름은 공백이 불가합니다.")
    private String name;

    @NotEmpty(message = "상품 가격은 공백이 불가합니다.")
    private int price;

    @NotEmpty(message = "상품 설명은 공백이 불가합니다.")
    private String detail;

    @NotEmpty(message = "상품 수량은 공백이 불가합니다.")
    private int inventory;
}