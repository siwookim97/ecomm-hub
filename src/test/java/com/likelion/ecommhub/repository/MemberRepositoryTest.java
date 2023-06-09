package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Cart;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.MemberRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void init() {
        Member buyer_A = new Member("buyer_A", "buyer1234", "구매자1",
                "buyer_A@gmail.com", "010-1111-1111", "서울시 노원구",
                 MemberRole.ROLE_BUYER);
        Member buyer_B = new Member("buyer_B", "buyer1234", "구매자2",
                "buyer_B@hanmail.net", "010-2222-2222", "창원시 귀현동",
                MemberRole.ROLE_BUYER);
        Member buyer_C = new Member("buyer_A", "buyer1234", "구매자3",
                "buyer_C@naver.com", "010-3333-3333", "서울시 반포동",
                MemberRole.ROLE_BUYER);
        memberRepository.save(buyer_A);
        memberRepository.save(buyer_B);
        memberRepository.save(buyer_C);

        Member seller_A = new Member("seller_A", "seller1234", "판매자1",
                "seller_A@naver.com", "010-4444-4444", "서울시 서초구",
                MemberRole.ROLE_SELLER ,"343-11111-111111");
        Member seller_B = new Member("seller_B", "seller1234", "판매자2",
                "seller_B@gmail.com", "010-4444-4444", "서울시 강남구",
                MemberRole.ROLE_SELLER, "434-33333-333333");
        memberRepository.save(seller_A);
        memberRepository.save(seller_B);
    }

    @Test
    @DisplayName("Member 엔티티 save 확인 테스트")
    void memberInsertTest() {
        // give

        // when
        int count_buyer = memberRepository.findByMemberRole(MemberRole.ROLE_BUYER).size();
        int count_seller = memberRepository.findByMemberRole(MemberRole.ROLE_SELLER).size();
        int countAll = memberRepository.findAll().size();

        // then
        assertThat(count_buyer).isEqualTo(3);
        assertThat(count_seller).isEqualTo(2);
        assertThat(countAll).isEqualTo(5);
    }
}