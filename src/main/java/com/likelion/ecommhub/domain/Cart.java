package com.likelion.ecommhub.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Cart extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;

	private Long orderId;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	private String productName;
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	private int productCount;
	private int price;
	private int totalPrice;
	private int quantity;

	public Cart(Long cartId, Long orderId, Product product,String productName, Member member, int productCount, int price, int totalPrice,
		int quantity) {
		this.cartId = cartId;
		this.orderId = orderId;
		this.product = product;
		this.productName = productName;
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

	public void setMember(Member member) {
		this.member = member;
	}
}