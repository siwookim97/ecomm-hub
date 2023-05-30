package com.likelion.ecommhub.service;

import com.likelion.ecommhub.config.auth.JwtProvider;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.MemberRole;
import com.likelion.ecommhub.dto.MemberJoinDto;
import com.likelion.ecommhub.repository.MemberRepository;
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
    private final JwtProvider jwtProvider;

    @Transactional
    public String joinSeller(MemberJoinDto request) {
        if (memberRepository.findByLoginId(request.getLoginId()).isPresent()) {
            return "JOINSELLER 실패";
        }

        Member createdMember = createMember(request, MemberRole.ROLE_SELLER);
        memberRepository.save(createdMember);

        return "JOINSELLER 성공";
    }

    @Transactional
    public String joinBuyer(MemberJoinDto request) {
        if (memberRepository.findByLoginId(request.getLoginId()).isPresent()) {
            return "JOINBUYER 실패";
        }

        Member createdMember = createMember(request, MemberRole.ROLE_BUYER);
        memberRepository.save(createdMember);

        return "JOINBUYER 성공";
    }

    private Member createMember(MemberJoinDto request, MemberRole memberRole) {
        return new Member(
                request.getLoginId(),
                encoder.encode(request.getPassword()),
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getAddress(),
                memberRole,
                request.getAccount()
        );
    }
}