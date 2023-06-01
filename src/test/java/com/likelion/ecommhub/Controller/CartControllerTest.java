package com.likelion.ecommhub.Controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
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
import com.likelion.ecommhub.service.CartService;

@SpringBootTest
@Transactional
public class CartControllerTest {

	@Autowired
	private CartController cartController;

	@Autowired
	private CartService cartService;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Test
	public void testGetCart() {

		Cart cart = new Cart();

		cartRepository.save(cart);


		Optional<Cart> result = cartController.getCart(cart.getCartId());

		assertTrue(result.isPresent());
		assertEquals(cart.getCartId(), result.get().getCartId());

		cartRepository.delete(cart);
	}

	@Test
	public void testAddToCart() {

		Cart cart = new Cart();


		Product product = new Product("상품1",100,"상품상세설명1",10);



		cartRepository.save(cart);
		productRepository.save(product);


		ResponseEntity<String> response = cartController.addToCart(cart.getCartId(), product.getId(), 1);


		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("상품이 장바구니에 추가되었습니다.", response.getBody());


		Optional<Cart> updatedCart = cartRepository.findById(cart.getCartId());
		assertTrue(updatedCart.isPresent());



		cartRepository.delete(cart);
		productRepository.delete(product);
	}



}
