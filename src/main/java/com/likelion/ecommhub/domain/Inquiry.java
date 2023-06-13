package com.likelion.ecommhub.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Inquiry(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setProduct(Product product) {
        if (this.product != null) {
            this.product.getInquiries().remove(this);
        }
        this.product = product;
        product.getInquiries().add(this);
    }

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getInquiries().remove(this);
        }
        this.member = member;
        member.getInquiries().add(this);
    }
}
