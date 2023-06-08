package com.likelion.ecommhub.initData;

import com.likelion.ecommhub.domain.CartItem;
import com.likelion.ecommhub.repository.CartItemRepository;
import java.util.List;
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
import org.springframework.transaction.annotation.Transactional;

@Configuration

public class NotProd {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final BCryptPasswordEncoder encoder;
    private final CartItemRepository cartItemRepository;

    public NotProd(MemberRepository memberRepository, CartRepository cartRepository,
                   ProductRepository productRepository, CartItemRepository cartItemRepository, BCryptPasswordEncoder encoder) {
        this.memberRepository = memberRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    @Transactional
    public void initializeData() {
        cartRepository.deleteAll();
        memberRepository.deleteAll();
        productRepository.deleteAll();
        cartItemRepository.deleteAll();

        Member member1 = new Member("loginId1", encoder.encode("password1"), "Seller1",
                "seller1@email.com", "010-1234-1234", "address1",
                MemberRole.ROLE_SELLER);

        Member member2 = new Member("loginId2", encoder.encode("password2"), "Seller2",
                "seller2@email.com", "010-1111-1111", "address2",
                MemberRole.ROLE_SELLER);
        Member member3 = new Member("user", encoder.encode("user"), "user",
            "user2@email.com", "010-2222-1111", "address",
            MemberRole.ROLE_BUYER);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

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
    }
}
