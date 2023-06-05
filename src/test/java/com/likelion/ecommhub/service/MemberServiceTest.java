package com.likelion.ecommhub.service;

import com.likelion.ecommhub.dto.MemberJoinDto;
import com.likelion.ecommhub.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void init() {
        memberService.joinSeller(new MemberJoinDto("seller", "seller_password", "seller_name",
                "seller@google.com", "010-1111-1111", "seller_address",1, "account"));
        memberService.joinBuyer(new MemberJoinDto("buyer", "buyer_password", "buyer_name",
                "buyer@google.com", "010-2222-2222", "buyer_address",1, null));
    }

    @Test
    @DisplayName("회원가입 실패 테스트")
    @Rollback(value = false)
    void memberJoinFailTest() {
        // when
        memberService.joinSeller(new MemberJoinDto("seller", "failSeller_password", "failSeller_name",
                "failSeller@google.com", "010-1234-1234", "failSeller_address", 2,"fail_account"));
        memberService.joinBuyer(new MemberJoinDto("buyer", "failBuyer_password", "failBuyer_name",
                "failBuyer@google.com", "010-2345-2345", "failBuyer_address", 3,null));

        // then
        Assertions.assertThat(memberRepository.findByUsername("seller").get().getNickname()).isNotEqualTo("failSeller_name");
        Assertions.assertThat(memberRepository.findByUsername("buyer").get().getNickname()).isNotEqualTo("failBuyer_name");
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void memberJoinSuccessTest() {
        // when
        memberService.joinSeller(new MemberJoinDto("successSeller", "successSeller_password", "successSeller_name",
                "successSeller@google.com", "010-1234-1234", "successSeller_address", 1,"success_account"));
        memberService.joinBuyer(new MemberJoinDto("successBuyer", "successBuyer_password", "successBuyer_name",
                "successBuyer@google.com", "010-2345-2345", "successBuyer_address",1, null));

        // then
        Assertions.assertThat(memberRepository.findByUsername("successSeller").isPresent()).isTrue();
        Assertions.assertThat(memberRepository.findByUsername("successBuyer").isPresent()).isTrue();
    }
}