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

import java.util.Optional;

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

    public String login(String loginId, String password) {
        //TODO Rs 리턴 후 예외처리
        Optional<Member> selectedOptionalMember = memberRepository.findByLoginId(loginId);

        if (selectedOptionalMember.isEmpty()) {
            return loginId + "회원이(가) 없습니다.";
        }

        if (!encoder.matches(password, selectedOptionalMember.get().getPassword())) {
            return loginId + "회원의 비밀번호가 틀렸습니다.";
        }

        return jwtProvider.createToken(selectedOptionalMember.get().getLoginId());
    }

    private Member createMember(MemberJoinDto requestDto, MemberRole memberRole) {
        return new Member(
                requestDto.getLoginId(),
                encoder.encode(requestDto.getPassword()),
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPhone(),
                requestDto.getAddress(),
                memberRole,
                requestDto.getAccount()
        );
    }
}