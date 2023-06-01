package com.likelion.ecommhub.initData;

import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.MemberRole;
import com.likelion.ecommhub.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class NotProd implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    public NotProd(MemberRepository memberRepository, BCryptPasswordEncoder encoder) {
        this.memberRepository = memberRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Member member1 = new Member("loginId1", encoder.encode("password1"), "Seller1",
            "seller1@email.com", "010-1234-1234", "address1",
            MemberRole.ROLE_SELLER, "account1");

        Member member2 = new Member("loginId2", encoder.encode("password2"), "Seller2",
            "seller2@email.com", "010-1111-1111", "address2",
            MemberRole.ROLE_SELLER, "account2");

        memberRepository.save(member1);
        memberRepository.save(member2);
    }
}