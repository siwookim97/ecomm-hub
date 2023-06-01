package com.likelion.ecommhub.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.likelion.ecommhub.domain.Cart;
import com.likelion.ecommhub.repository.CartRepository;
import com.likelion.ecommhub.repository.ProductRepository;

@Service
public class CartService {

	private final CartRepository cartRepository;
	private final ProductRepository productRepository;

	public CartService(CartRepository cartRepository, ProductRepository productRepository) {
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
	}

	public Optional<Cart> getCartById(Long cartId) {
		return cartRepository.findById(cartId);
	}

	public void addToCart(Long cartId, Long productId, int quantity) {

	}

	public void removeFromCart(Long cartId, Long productId) {

	}

	public void updateCartItemQuantity(Long cartId, Long productId, int quantity) {

	}


}
