package com.likelion.ecommhub.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.likelion.ecommhub.domain.Cart;
import com.likelion.ecommhub.domain.CartItem;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.repository.CartItemRepository;
import com.likelion.ecommhub.repository.CartRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;


    //Member에게 장바구니 생성
    public void createCart(Member member) {
        Cart cart = Cart.createCart(member);
        cartRepository.save(cart);
    }

    public Cart findMemberCart(Long memberId) {

        return cartRepository.findByMemberId(memberId);

    }

    public void addCart(Member member, Product product, int count) {
        Cart cart = cartRepository.findByMemberId(member.getId());

        // cart가 비어있다면 생성
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        // cart_item 생성 또는 수정
        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (cartItem == null) {
            cartItem = new CartItem(cart, product, count);
            cart.addCartItem(cartItem);  // cart와 cartItem 간의 관계 설정
            cartItemRepository.save(cartItem);  // cartItem 저장
        } else {
            // 비어있지 않다면 그만큼 갯수를 추가.
            cartItem.addCount(count);
            cartItemRepository.save(cartItem);  // cartItem 저장
        }

        cart.setCartItemCount(cart.getCartItemCount() + count);  // cart의 cart_item_count 업데이트
        cartRepository.save(cart);  // cart 저장
    }

    //장바구니 조회하기
    public List<CartItem> MemberCartView(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findAll();
        List<CartItem> MemberItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getCart().getId().equals(cart.getId())) {
                MemberItems.add(cartItem);
            }
        }
//        cart.makeName();
        return MemberItems;
    }

    //장바구니 Item 삭제하기
    public void cartItemDelete(long id) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(id);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            Cart cart = cartItem.getCart();
            cart.setCartItemCount(cart.getCartItemCount() - 1);
            cart.getCartItems().remove(cartItem);
            cartRepository.save(cart);
            cartItemRepository.deleteById(id);
        }
    }

    //장바구니 Item 전체삭제
    public void cartDelete(long id) {
        List<CartItem> cartItems = cartItemRepository.findAll(); // 전체 cart_item 찾기

        // 반복문을 이용하여 접속 Member의 Cart_item 만 찾아서 삭제
        for (CartItem cartItem : cartItems) {
            if (cartItem.getCart().getMember().getId() == id) {
                cartItem.getCart().setCartItemCount(cartItem.getCart().getCartItemCount() - 1);
                cartItemRepository.deleteById(cartItem.getId());
            }
        }
    }
}
