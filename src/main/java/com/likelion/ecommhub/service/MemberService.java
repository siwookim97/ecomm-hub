package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Cart;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.MemberRole;
import com.likelion.ecommhub.dto.MemberJoinDto;
import com.likelion.ecommhub.repository.MemberRepository;
import com.likelion.ecommhub.util.RsData;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional
    public RsData<Member> join(MemberJoinDto request) {
        if ( findByUsername(request.getUsername()).isPresent()) {
            return RsData.of("F-1","해당 아이디(%s)는 이미 사용중입니다.".formatted(request.getUsername()));
        }

        Member createdMember = createMember(request, MemberRole.ROLE_SELLER);
        memberRepository.save(createdMember);

        return RsData.of("S-1","회원가입이 완료되었습니다.",createdMember);
    }
    @Transactional
    public String joinSeller(MemberJoinDto request) {
        if (memberRepository.findByUsername(request.getUsername()).isPresent()) {
            return "JOINSELLER 실패";
        }

        Member createdMember = createMember(request, MemberRole.ROLE_SELLER);
        memberRepository.save(createdMember);

        return "JOINSELLER성공";
    }

    @Transactional
    public String joinBuyer(MemberJoinDto request) {
        if (memberRepository.findByUsername(request.getUsername()).isPresent()) {
            return "JOINBUYER 실패";
        }

        Member createdMember = createMember(request, MemberRole.ROLE_BUYER);
        memberRepository.save(createdMember);

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
            requestDto.getAccount(),
            new Cart()
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


}
