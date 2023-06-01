package com.likelion.ecommhub.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.likelion.ecommhub.dto.CartDto;

@Service
public class CartService {

	private List<CartDto> cartItems = new ArrayList<>();

	public List<CartDto> getCartItems() {
		return cartItems;
	}

	public void updateCartItemQuantity(Long productId, int quantity) {
		for (CartDto cartItem : cartItems) {
			if (cartItem.getProductId().equals(productId)) {
				cartItem.setQuantity(quantity);
				cartItem.setTotalPrice(cartItem.getPrice() * quantity);
				break;
			}
		}
	}

	public void removeCartItem(Long productId) {
		cartItems.removeIf(cartItem -> cartItem.getProductId().equals(productId));
	}
}