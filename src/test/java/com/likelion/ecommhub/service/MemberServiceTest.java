package com.likelion.ecommhub.Service;

import com.likelion.ecommhub.domain.MemberRole;
import com.likelion.ecommhub.dto.MemberJoinDto;
import com.likelion.ecommhub.repository.MemberRepository;
import com.likelion.ecommhub.service.MemberService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
                "seller@google.com", "010-1111-1111", "seller_address", "account"));
        memberService.joinBuyer(new MemberJoinDto("buyer", "buyer_password", "buyer_name",
                "buyer@google.com", "010-2222-2222", "buyer_address", null));
    }

    @Test
    @DisplayName("회원가입 실패 테스트")
    @Rollback(value = false)
    void memberJoinFailTest() {
        // when
        memberService.joinSeller(new MemberJoinDto("seller", "failSeller_password", "failSeller_name",
                "failSeller@google.com", "010-1234-1234", "failSeller_address", "fail_account"));
        memberService.joinBuyer(new MemberJoinDto("buyer", "failBuyer_password", "failBuyer_name",
                "failBuyer@google.com", "010-2345-2345", "failBuyer_address", null));

        // then
        Assertions.assertThat(memberRepository.findByLoginId("seller").get().getName()).isNotEqualTo("failSeller_name");
        Assertions.assertThat(memberRepository.findByLoginId("buyer").get().getName()).isNotEqualTo("failBuyer_name");
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void memberJoinSuccessTest() {
        // when
        memberService.joinSeller(new MemberJoinDto("successSeller", "successSeller_password", "successSeller_name",
                "successSeller@google.com", "010-1234-1234", "successSeller_address", "success_account"));
        memberService.joinBuyer(new MemberJoinDto("successBuyer", "successBuyer_password", "successBuyer_name",
                "successBuyer@google.com", "010-2345-2345", "successBuyer_address", null));

        // then
        Assertions.assertThat(memberRepository.findByLoginId("successSeller").isPresent()).isTrue();
        Assertions.assertThat(memberRepository.findByLoginId("successBuyer").isPresent()).isTrue();
    }
}