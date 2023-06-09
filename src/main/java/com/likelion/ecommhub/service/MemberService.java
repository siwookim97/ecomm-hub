package com.likelion.ecommhub.service;

import static com.likelion.ecommhub.domain.Cart.createCart;
import static com.likelion.ecommhub.domain.Order.createOrder;

import com.likelion.ecommhub.domain.Cart;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.MemberRole;
import com.likelion.ecommhub.domain.Order;
import com.likelion.ecommhub.dto.MemberJoinDto;
import com.likelion.ecommhub.repository.MemberRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public String joinSeller(MemberJoinDto request) {
        if (memberRepository.findByUsername(request.getUsername()).isPresent()) {
            return "JOINSELLER 실패";
        }

        Member createdMember = createMember(request, MemberRole.ROLE_SELLER);
        memberRepository.save(createdMember);

        return "JOINSELLER 성공";
    }

    @Transactional
    public String joinBuyer(MemberJoinDto request) {
        if (memberRepository.findByUsername(request.getUsername()).isPresent()) {
            return "JOINBUYER 실패";
        }

        Member createdMember = createMember(request, MemberRole.ROLE_BUYER);
        memberRepository.save(createdMember);
        createCart(createdMember);
        createOrder(createdMember);

        return "JOINBUYER 성공";
    }

    private Member createMember(MemberJoinDto requestDto, MemberRole memberRole) {
        return new Member(
            requestDto.getUsername(),
            encoder.encode(requestDto.getPassword()),
            requestDto.getNickname(),
            requestDto.getEmail(),
            requestDto.getPhone(),
            requestDto.getAddress(),
            memberRole,
            requestDto.getAccount()
        );

    }

    public Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username).get();
    }

    public Optional<Member> getMemberId(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Member findByNameFromSeller(MemberRole memberRole, String nickname) {
        return memberRepository.findByMemberRoleAndNickname(memberRole, nickname);
    }

    @Transactional
    public void memberModify(Long id, Member member) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member update = optionalMember.get();
            update.setNickname(member.getNickname());
            update.setEmail(member.getEmail());
            update.setAddress(member.getAddress());
            update.setPhone(member.getPhone());
            memberRepository.save(update);
        } else {
            throw new NoSuchElementException("이 아이디를 가진 유저가 없습니다 " + id);
        }
    }

}
