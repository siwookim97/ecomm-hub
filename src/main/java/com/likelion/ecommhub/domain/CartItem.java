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
import lombok.Builder;
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

	@Builder
	public CartItem(Cart cart, Product product, int count){
		this.cart =cart;
		this.product =product;
		this.count =count;
	}

	public void setCart(Cart cart) {
		if (this.cart != null) {
			this.cart.getCartItems().remove(this);
		}
		this.cart = cart;
		cart.getCartItems().add(this);
	}

	public void setProduct(Product product) {
		if (this.product != null) {
			this.product.getCartItems().remove(this);
		}
		this.product = product;
		product.getCartItems().add(this);
	}

	public void addCount(int count) {
		this.count += count;
	}
}


