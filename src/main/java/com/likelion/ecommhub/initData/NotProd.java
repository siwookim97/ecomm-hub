package com.likelion.ecommhub.initData;

import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.MemberRole;
import com.likelion.ecommhub.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NotProd implements CommandLineRunner {

    private final MemberRepository memberRepository;

    public NotProd(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Member member1 = new Member("loginId1", "password1", "Seller1",
            "seller1@email.com", "phone1", "address1",
            MemberRole.ROLE_SELLER, "account1");

        Member member2 = new Member("loginId2", "password2", "Seller2",
            "seller2@email.com", "phone2", "address2",
            MemberRole.ROLE_SELLER, "account2");

        memberRepository.save(member1);
        memberRepository.save(member2);
    }
}