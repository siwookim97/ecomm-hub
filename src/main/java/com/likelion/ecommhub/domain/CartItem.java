package com.likelion.ecommhub.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id", referencedColumnName = "id")
	private Cart cart;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	private int count; // 카트에 담긴 상품 개수

	@Column(nullable = false)
	private LocalDate createDate; // 날짜

	@PrePersist
	public void setCreateDate() {
		this.createDate = LocalDate.now();
	}

	public static CartItem createCartItem(Cart cart, Product product, int count) {
		CartItem cartItem = new CartItem();
		cartItem.setCart(cart);
		cartItem.setProduct(product);
		cartItem.setCount(count);
		cartItem.setCreateDate();

		// cart와 cartItem 간의 양방향 관계 설정
		cart.getCartItems().add(cartItem);
		cartItem.setCart(cart);

		// product와 cartItem 간의 양방향 관계 설정
		product.getCartItems().add(cartItem);
		cartItem.setProduct(product);

		return cartItem;
	}

	private void setCount(int count) {
		this.count = count;
	}

	private void setProduct(Product product) {
		this.product = product;
	}

	void setCart(Cart cart) {
		this.cart = cart;
	}

	public void addCount(int count) {
		this.count += count;
	}
}


