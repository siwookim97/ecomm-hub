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

    private int paid;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inquiry> inquiries = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();


    public Member(String username, String password, String nickname,
        String email, String phone, String address,
        MemberRole memberRole,int paid) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.memberRole = memberRole;
        this.paid = paid;
    }

    public Member(String username, String password, String nickname,
        String email, String phone, String address,
        MemberRole memberRole,String account, Cart cart,int paid) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.memberRole = memberRole;
        this.account = account;
        this.cart = cart;
        this.paid = paid;
    }

    public void setPassword(String encPassword) {
        this.password =password;
    }

    public void setMemberRole(String roleGuest) {
        this.memberRole =memberRole;
        this.password = password;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }
}
