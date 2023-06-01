package com.likelion.ecommhub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
	private Long productId;
	private String productName;
	private int price;
	private int quantity;
	private int totalPrice;

	// 생성자, getter, setter 생략
}