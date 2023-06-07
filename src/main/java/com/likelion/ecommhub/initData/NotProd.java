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
                MemberRole.ROLE_SELLER);

        Member member2 = new Member("loginId2", encoder.encode("password2"), "Seller2",
                "seller2@email.com", "010-1111-1111", "address2",
                MemberRole.ROLE_SELLER);

        memberRepository.save(member1);
        memberRepository.save(member2);

        for (int i = 1; i <= 300; i++) {
            String name = String.format("상품%d",i);
            Product product = new Product(name, 100, "상품상세설명",
                    (i % 2) == 0 ? 10 : 0,
                    (i % 2) == 0 ? ProductState.ON_SALE : ProductState.SOLD_OUT);
            if (i % 2 == 0) {
                product.setMember(member1);
            }
            else {
                product.setMember(member2);
            }
            productRepository.save(product);
        }
    }
//    private final MemberRepository memberRepository;
//    private final CartRepository cartRepository;
//    private final ProductRepository productRepository;
//    private final BCryptPasswordEncoder encoder;
//
//    public NotProd(MemberRepository memberRepository, CartRepository cartRepository,
//                   ProductRepository productRepository, BCryptPasswordEncoder encoder) {
//        this.memberRepository = memberRepository;
//        this.cartRepository = cartRepository;
//        this.productRepository = productRepository;
//        this.encoder = encoder;
//    }
//
//    @PostConstruct
//    public void initializeData() {
//        cartRepository.deleteAll();
//        memberRepository.deleteAll();
//        productRepository.deleteAll();
//
//        Member member1 = new Member("loginId1", encoder.encode("password1"), "Seller1",
//                "seller1@email.com", "010-1234-1234", "address1",
//                MemberRole.ROLE_SELLER);
//
//        Member member2 = new Member("loginId2", encoder.encode("password2"), "Seller2",
//                "seller2@email.com", "010-1111-1111", "address2",
//                MemberRole.ROLE_SELLER);
//
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//
//        Product product1 = new Product("상품1", 100, "상품상세설명1", 10, ProductState.ON_SALE);
//        productRepository.save(product1);
//
//        Product product2 = new Product("상품2", 200, "상품상세설명2", 20,ProductState.ON_SALE);
//        productRepository.save(product2);
//    }
}
