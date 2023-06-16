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
    private final NoticeRepository noticeRepository;

    @PostConstruct
    @Transactional
    public void initializeData() {
        cartRepository.deleteAll();
        memberRepository.deleteAll();
        productRepository.deleteAll();
        cartItemRepository.deleteAll();
        salesRepository.deleteAll();
        noticeRepository.deleteAll();

        Member admin = new Member("admin", encoder.encode("admin"), "admin",
            "admin@email.com", "01012341233", "adminAddress", MemberRole.ROLE_ADMIN);

        memberRepository.save(admin);
//
//        for (int i = 1; i <= 100; i++) {
//            String name = String.format("상품%d", i);
//            Product product = new Product(name, 100, "상품상세설명",
//                (i % 2) == 0 ? 10 : 0,
//                (i % 2) == 0 ? ProductState.ON_SALE : ProductState.SOLD_OUT);
//            if (i % 2 == 0) {
//                product.setMember(member1);
//            } else {
//                product.setMember(member2);
//            }
//            productRepository.save(product);
//        }
//        List<Member> members = memberRepository.findAll();
//        List<Product> products = productRepository.findAll();
//
//        for (Member member : members) {
//            if (member.getMemberRole() == MemberRole.ROLE_BUYER) {
//                Cart cart = Cart.createCart(member);
//                cartRepository.save(cart);
//
//                for (int i = 0; i < 10; i++) {
//                    Product product = products.get(i);
//                    int quantity = (i % 2) + 1;
//
//                    CartItem cartItem = new CartItem(cart, product, quantity);
//                    cart.addCartItem(cartItem);
//
//                    cartItemRepository.save(cartItem);
//                }
//
//                cart.setCartItemCount(cart.getCartItems().size());
//                cartRepository.save(cart);
//            }
//        }
//
//        List<Integer> salesData = Arrays.asList(1000, 2000, 1500, 2500, 1800, 2200, 1900, 3000, 2800, 3500, 3200, 4000);
//
//        int year = 2023;
//        LocalDateTime saleDate = LocalDateTime.now();
//
//        for (int i = 0; i < salesData.size(); i++) {
//            int month = i + 1;
//            BigDecimal sales = BigDecimal.valueOf(salesData.get(i));
//
//            Sales salesEntry = Sales.builder()
//                .sales(sales)
//                .saleDate(saleDate.withMonth(month))
//                .saleYear(year)
//                .member(member1)
//                .build();
//
//            salesRepository.save(salesEntry);
//        }
//
//        Notice notice1 = new Notice(NoticeType.SYSTEM,"제목1","내용1");
//        Notice notice2 = new Notice(NoticeType.SYSTEM,"제목2","내용2");
//
//        noticeRepository.save(notice1);
//        noticeRepository.save(notice2);
    }
}
