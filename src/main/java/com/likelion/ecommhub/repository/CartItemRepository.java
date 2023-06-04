package com.likelion.ecommhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.likelion.ecommhub.domain.Cart;
import com.likelion.ecommhub.domain.CartItem;
import com.likelion.ecommhub.domain.Product;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

	CartItem findByCartAndProduct(Cart cart, Product product);
}