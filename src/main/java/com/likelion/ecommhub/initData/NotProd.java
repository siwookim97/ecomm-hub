package com.likelion.ecommhub.initData;

import javax.annotation.PostConstruct;

import com.likelion.ecommhub.domain.Cart;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.MemberRole;

import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.domain.ProductState;
import com.likelion.ecommhub.repository.CartRepository;
import com.likelion.ecommhub.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.likelion.ecommhub.repository.ProductRepository;

import org.springframework.context.annotation.Configuration;

@Configuration

public class NotProd {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final BCryptPasswordEncoder encoder;

    public NotProd(MemberRepository memberRepository, CartRepository cartRepository,
                   ProductRepository productRepository, BCryptPasswordEncoder encoder) {
        this.memberRepository = memberRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    public void initializeData() {
        cartRepository.deleteAll();
        memberRepository.deleteAll();
        productRepository.deleteAll();

        Member member1 = new Member("loginId1", encoder.encode("password1"), "Seller1",
                "seller1@email.com", "010-1234-1234", "address1",
                MemberRole.ROLE_SELLER, "account1");

        Member member2 = new Member("loginId2", encoder.encode("password2"), "Seller2",
                "seller2@email.com", "010-1111-1111", "address2",
                MemberRole.ROLE_SELLER, "account2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        Product product1 = new Product("상품1", 100, "상품상세설명1", 10, ProductState.ON_SALE);
        productRepository.save(product1);

        Product product2 = new Product("상품2", 200, "상품상세설명2", 20,ProductState.ON_SALE);
        productRepository.save(product2);

        Cart cart1 = new Cart(1L, 1L, product1,"상품1", member1, 10, 100, 1000, 10);
        cartRepository.save(cart1);

        Cart cart2 = new Cart(2L, 2L, product2,"상품2", member2, 10, 200, 4000, 20);
        cartRepository.save(cart2);

        member1.addCart(cart1);
        member2.addCart(cart2);

        memberRepository.save(member1);
        memberRepository.save(member2);
    }
}
