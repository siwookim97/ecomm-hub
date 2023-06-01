package com.likelion.ecommhub.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cart extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;

	private Long orderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	private int productCount;
	private int price;
	private int totalPrice;
	private int quantity;

	public Cart(Long cartId, Long orderId, Product product, Member member, int productCount, int price, int totalPrice,
		int quantity) {
		this.cartId = cartId;
		this.orderId = orderId;
		this.product = product;
		this.member = member;
		this.productCount = productCount;
		this.price = price;
		this.totalPrice = totalPrice;
		this.quantity = quantity;
	}

	public Cart(Product product, int productCount, int price, int totalPrice, int quantity) {
		this.product = product;
		this.productCount = productCount;
		this.price = price;
		this.totalPrice = totalPrice;
		this.quantity = quantity;
	}


}