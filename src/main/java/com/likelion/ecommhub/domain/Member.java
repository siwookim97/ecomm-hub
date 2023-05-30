package com.likelion.ecommhub.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String email;

    @Column(nullable = false)
    private String phone; // 기존 seller_number에서 변경

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private String account; // 계좌번호는 String을 권장 (ex, 019-1234136-1234)

    public Member(String loginId, String password, String name,
                  String email, String phone, String address,
                  MemberRole memberRole) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.memberRole = memberRole;
    }

    public Member(String loginId, String password, String name,
                  String email, String phone, String address,
                  MemberRole memberRole, String account) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.memberRole = memberRole;
        this.account = account;
    }
}
