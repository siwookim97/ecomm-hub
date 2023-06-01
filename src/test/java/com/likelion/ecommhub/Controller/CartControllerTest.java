package com.likelion.ecommhub.Controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.likelion.ecommhub.controller.CartController;
import com.likelion.ecommhub.domain.Cart;
import com.likelion.ecommhub.domain.Product;

import com.likelion.ecommhub.repository.CartRepository;
import com.likelion.ecommhub.repository.ProductRepository;

@SpringBootTest
@Transactional
public class CartControllerTest {

	@Autowired
	private CartController cartController;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Test
	@DisplayName("장바구니 생성")
	public void testGetCart() {

		Cart cart = new Cart();
		cartRepository.save(cart);

		Optional<Cart> result = cartController.getCart(cart.getCartId());

		assertTrue(result.isPresent());
		assertEquals(cart.getCartId(), result.get().getCartId());

		cartRepository.delete(cart);
	}

	@Test
	@DisplayName("장바구니에 상품 추가")
	public void testAddToCart() {

		Cart cart = new Cart();
		cartRepository.save(cart);

		Product product = new Product("상품1",100,"상품상세설명1",10);
		productRepository.save(product);

		ResponseEntity<String> response = cartController.addToCart(cart.getCartId(), product.getId(), 1);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("상품이 장바구니에 추가되었습니다.", response.getBody());

		Optional<Cart> updatedCart = cartRepository.findById(cart.getCartId());
		assertTrue(updatedCart.isPresent());


		cartRepository.delete(cart);
		productRepository.delete(product);
	}
	@Test
	@DisplayName("장바구니에 상품 개수 수정")
	public void testUpdateCartItem() {

		Cart cart = new Cart();
		cartRepository.save(cart);


		Product product = new Product("상품1", 100, "상품상세설명1", 10);
		productRepository.save(product);


		ResponseEntity<String> addToCartResponse = cartController.addToCart(cart.getCartId(), product.getId(), 1);
		assertEquals(HttpStatus.OK, addToCartResponse.getStatusCode());
		assertEquals("상품이 장바구니에 추가되었습니다.", addToCartResponse.getBody());


		ResponseEntity<String> updateCartItemResponse = cartController.updateCartItemQuantity(cart.getCartId(), product.getId(), 5);
		assertEquals(HttpStatus.OK, updateCartItemResponse.getStatusCode());
		assertEquals("상품 수량이 업데이트되었습니다.", updateCartItemResponse.getBody());


		Optional<Cart> updatedCart = cartRepository.findById(cart.getCartId());
		assertTrue(updatedCart.isPresent());
		assertEquals(0, updatedCart.get().getQuantity());


		cartRepository.delete(cart);
		productRepository.delete(product);
	}

	@Test
	@DisplayName("장바구니에 상품 삭제")
	public void testRemoveFromCart() {

		Cart cart = new Cart();
		cartRepository.save(cart);


		Product product = new Product("상품1", 100, "상품상세설명1", 10);
		productRepository.save(product);


		ResponseEntity<String> addToCartResponse = cartController.addToCart(cart.getCartId(), product.getId(), 1);
		assertEquals(HttpStatus.OK, addToCartResponse.getStatusCode());
		assertEquals("상품이 장바구니에 추가되었습니다.", addToCartResponse.getBody());


		ResponseEntity<String> removeFromCartResponse = cartController.removeFromCart(cart.getCartId(), product.getId());
		assertEquals(HttpStatus.OK, removeFromCartResponse.getStatusCode());
		assertEquals("상품이 장바구니에서 삭제되었습니다.", removeFromCartResponse.getBody());


		Optional<Cart> updatedCart = cartRepository.findById(cart.getCartId());
		assertTrue(updatedCart.isPresent());
		assertFalse(updatedCart.get().equals(product));


		cartRepository.delete(cart);
		productRepository.delete(product);
	}


}
