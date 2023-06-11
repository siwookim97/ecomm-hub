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

	@OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CartItem> cartItems = new ArrayList<>();

	public Cart(int cartItemCount, Member member) {
		this.cartItemCount = cartItemCount;
		this.member = member;
	}

	public static Cart createCart(Member member){
		return new Cart(0, member);
	}

  
	public void addCartItem(CartItem cartItem) {
		cartItems.add(cartItem);
		cartItem.setCart(this);
	}
  
	public void setCartItemCount(int cartItemCount) {
		this.cartItemCount =cartItemCount;
	}

}