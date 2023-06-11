package com.likelion.ecommhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @NotEmpty(message = "상품 이름은 공백이 불가합니다.")
    private String name;

    @NotNull(message = "상품 가격은 공백이 불가합니다.")
    private int price;

    @NotEmpty(message = "상품 설명은 공백이 불가합니다.")
    private String detail;

    @NotNull(message = "상품 수량은 공백이 불가합니다.")
    private int inventory;

    private MultipartFile image;
}