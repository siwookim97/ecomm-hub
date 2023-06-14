package com.likelion.ecommhub.initData;

import com.likelion.ecommhub.domain.*;
import com.likelion.ecommhub.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@AllArgsConstructor
public class NotProd {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final BCryptPasswordEncoder encoder;
    private final CartItemRepository cartItemRepository;
    private final SalesRepository salesRepository;

    @PostConstruct
    @Transactional
    public void initializeData() {
        cartRepository.deleteAll();
        memberRepository.deleteAll();
        productRepository.deleteAll();
        cartItemRepository.deleteAll();
        salesRepository.deleteAll();

        Member member1 = new Member("loginId1", encoder.encode("password1"), "Seller1",
            "seller1@email.com", "01012341234", "address1",
            MemberRole.ROLE_SELLER);

        Member member2 = new Member("loginId2", encoder.encode("password2"), "Seller2",
            "seller2@email.com", "01011111111", "address2",
            MemberRole.ROLE_SELLER);
        Member member3 = new Member("user", encoder.encode("user"), "user",
            "user2@email.com", "01022221111", "address",
            MemberRole.ROLE_BUYER);

        Member member4 = new Member("qwer", encoder.encode("qwer"), "qwer",
            "qwer@email.com", "01012341234", "qwerAddress", MemberRole.ROLE_BUYER);

        Member member5 = new Member("admin", encoder.encode("admin"), "admin",
            "admin@email.com", "01012341233", "adminAddress", MemberRole.ROLE_ADMIN);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        for (int i = 1; i <= 100; i++) {
            String name = String.format("상품%d", i);
            Product product = new Product(name, 100, "상품상세설명",
                (i % 2) == 0 ? 10 : 0,
                (i % 2) == 0 ? ProductState.ON_SALE : ProductState.SOLD_OUT);
            if (i % 2 == 0) {
                product.setMember(member1);
            } else {
                product.setMember(member2);
            }
            productRepository.save(product);
        }
        List<Member> members = memberRepository.findAll();
        List<Product> products = productRepository.findAll();

        for (Member member : members) {
            if (member.getMemberRole() == MemberRole.ROLE_BUYER) {
                Cart cart = Cart.createCart(member);
                cartRepository.save(cart);

                for (int i = 0; i < 10; i++) {
                    Product product = products.get(i);
                    int quantity = (i % 2) + 1;

                    CartItem cartItem = new CartItem(cart, product, quantity);
                    cart.addCartItem(cartItem);

                    cartItemRepository.save(cartItem);
                }

                cart.setCartItemCount(cart.getCartItems().size());
                cartRepository.save(cart);
            }
        }

        List<Integer> salesData = Arrays.asList(1000, 2000, 1500, 2500, 1800, 2200, 1900, 3000, 2800, 3500, 3200, 4000);

        for (int i = 0; i < salesData.size(); i++) {
            BigDecimal sales = BigDecimal.valueOf(salesData.get(i));

            Sales salesEntry = Sales.builder()
                .sales(sales)
                .saleDate(LocalDateTime.now())
                .saleYear(2023) // Set the appropriate year
                .member(member1) // Set the member for association
                .build();

            salesRepository.save(salesEntry);

        }
    }
}
