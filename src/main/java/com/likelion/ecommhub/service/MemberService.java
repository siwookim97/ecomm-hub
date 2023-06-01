package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.MemberRole;
import com.likelion.ecommhub.dto.MemberJoinDto;
import com.likelion.ecommhub.repository.MemberRepository;
<<<<<<< HEAD

import java.util.Optional;

=======
import java.util.List;
>>>>>>> 3eba8f14657462fff6528970d67c22454f509556
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


	public Optional<Member> getMemberId(Long memberId) {
        return memberRepository.findById(memberId);
	}

    public Member findByNameFromSeller(MemberRole memberRole, String username) {
        return memberRepository.findByMemberRoleAndNickname(memberRole, username);
    }

}