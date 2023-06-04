package com.likelion.ecommhub.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "USERNAME_UK", columnNames = {"USERNAME"})
})
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String email;

    @Column(nullable = false)
    private String phone; // 기존 seller_number에서 변경

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private String account; // 계좌번호는 String을 권장 (ex, 019-1234136-1234)

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inquiry> inquiries = new ArrayList<>();


    public Member(String username, String password, String nickname,
                  String email, String phone, String address,
                  MemberRole memberRole) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.memberRole = memberRole;
    }

    public Member(String username, String password, String nickname,
                  String email, String phone, String address,
                  MemberRole memberRole, String account) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.memberRole = memberRole;
        this.account = account;
    }

    public void addCart(Cart cart) {
        carts.add(cart);
        cart.setMember(this);
    }

    public void removeCart(Cart cart) {
        carts.remove(cart);
        cart.setMember(null);
    }
}