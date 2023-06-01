package com.likelion.ecommhub.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.likelion.ecommhub.domain.Cart;
import com.likelion.ecommhub.dto.MemberJoinDto;
import com.likelion.ecommhub.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping("/{cartId}")
	public Optional<Cart> getCart(@PathVariable Long cartId) {
		Optional<Cart> cartDto = cartService.getCartById(cartId);
		return cartDto;

	}
	@GetMapping("/{cartId}/list")
	public String getCartList(@PathVariable Long cartId, Model model) {
		Optional<Cart> cartDto = cartService.getCartById(cartId);
		model.addAttribute("cartDto", cartDto);
		return "cartList";

	}

	@PostMapping("/{cartId}/products/{productId}")
	public ResponseEntity<String> addToCart(
		@PathVariable Long cartId,
		@PathVariable Long productId,
		@RequestParam("quantity") int quantity) {

		try {
			cartService.addToCart(cartId, productId, quantity);
			return ResponseEntity.ok("상품이 장바구니에 추가되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("상품 추가에 실패했습니다.");
		}
	}

	@DeleteMapping("/{cartId}/products/{productId}")
	public ResponseEntity<String> removeFromCart(
		@PathVariable Long cartId,
		@PathVariable Long productId) {
		try {
			cartService.removeFromCart(cartId, productId);
			return ResponseEntity.ok("상품이 장바구니에서 제거되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("상품 제거에 실패했습니다.");
		}
	}

	@PutMapping("/{cartId}/products/{productId}")
	public ResponseEntity<String> updateCartItemQuantity(
		@PathVariable Long cartId,
		@PathVariable Long productId,
		@RequestParam("quantity") int quantity) {
		try {
			cartService.updateCartItemQuantity(cartId, productId,quantity);
			return ResponseEntity.ok("상품이 장바구니에서 수정되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("상품 수정에 실패했습니다.");
		}
	}
}
