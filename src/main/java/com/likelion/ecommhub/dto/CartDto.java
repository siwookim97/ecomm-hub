package com.likelion.ecommhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

	private Long cartId;
	private Long orderId;
	private Long productId;
	private String productName;
	private int productCount;
	private int price;
	private int totalPrice;
	private int quantity;
}
