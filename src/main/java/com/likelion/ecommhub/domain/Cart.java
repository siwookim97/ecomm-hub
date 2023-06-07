package com.likelion.ecommhub.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Cart extends BaseEntity {
	@Builder
	public Cart() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int cartItemCount; //카트에 담긴 상품의 종류

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "id")
	private Member member;

	@OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<CartItem> cartItems = new ArrayList<>();

	public static Cart createCart(Member member){
		Cart cart = new Cart();
		cart.member = member;
		cart.cartItemCount = 0;

		return cart;
	}

  
	public void addCartItem(CartItem cartItem) {
		cartItems.add(cartItem);
		cartItem.setCart(this);
		cartItem.getProduct().getCartItems().add(cartItem);
	}
  
	public void setCartItemCount(int cartItemCount) {
		this.cartItemCount =cartItemCount;
	}

}