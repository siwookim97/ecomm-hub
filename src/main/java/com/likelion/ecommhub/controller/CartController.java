package com.likelion.ecommhub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.likelion.ecommhub.dto.CartDto;
import com.likelion.ecommhub.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping("/{cartId}")
	public CartDto getCart(@PathVariable Long cartId) {

	}

	@PostMapping("/{cartId}/products/{productId}")
	public ResponseEntity<String> addToCart(
		@PathVariable Long cartId,
		@PathVariable Long productId,
		@RequestParam("quantity") int quantity) {

	}

	@DeleteMapping("/{cartId}/products/{productId}")
	public ResponseEntity<String> removeFromCart(
		@PathVariable Long cartId,
		@PathVariable Long productId) {

	}

	@PutMapping("/{cartId}/products/{productId}")
	public ResponseEntity<String> updateCartItemQuantity(
		@PathVariable Long cartId,
		@PathVariable Long productId,
		@RequestParam("quantity") int quantity) {

	}
}
