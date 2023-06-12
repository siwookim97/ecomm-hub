package com.likelion.ecommhub.service;

import static com.likelion.ecommhub.domain.Cart.createCart;
import static com.likelion.ecommhub.domain.Order.createOrder;

import com.likelion.ecommhub.domain.Cart;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.MemberRole;
import com.likelion.ecommhub.domain.Order;
import com.likelion.ecommhub.dto.MemberJoinDto;
import com.likelion.ecommhub.repository.MemberRepository;
import com.likelion.ecommhub.util.RsData;

import java.util.NoSuchElementException;
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
            return RsData.of("F-1", String.format("해당 아이디(%s)는 이미 사용중입니다.", request.getUsername()));
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
    public boolean emailDuplicationCheck(Long id, String email){
        Optional<Member> existingMember = memberRepository.findByEmail(email);
        if (existingMember.isPresent()) {
            if (!existingMember.get().getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    public boolean nicknameDuplicationCheck(Long id, String nickname){
        Optional<Member> existingMember = memberRepository.findByNickname(nickname);
        if (existingMember.isPresent()) {
            if (!existingMember.get().getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    public boolean addressDuplicationCheck(Long id, String address){
        Optional<Member> existingMember = memberRepository.findByAddress(address);
        if (existingMember.isPresent()) {
            if (!existingMember.get().getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    public boolean phoneDuplicationCheck(Long id, String phone){
        Optional<Member> existingMember = memberRepository.findByPhone(phone);
        if (existingMember.isPresent()) {
            if (!existingMember.get().getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void memberModify(Long id, Member member) throws Exception {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member update = optionalMember.get();
            if (member.getNickname() != null && !member.getNickname().isEmpty()) {
                if (nicknameDuplicationCheck(id, member.getNickname())) {
                    throw new Exception("이미 사용중인 닉네임입니다.");
                } else {
                    update.setNickname(member.getNickname());
                }
            }
            if (member.getEmail() != null && !member.getEmail().isEmpty()) {
                if (emailDuplicationCheck(id, member.getEmail())) {
                    throw new Exception("이미 사용중인 이메일입니다.");
                } else {
                    update.setEmail(member.getEmail());
                }
            }
            if (member.getAddress() != null && !member.getAddress().isEmpty()) {
                if (addressDuplicationCheck(id, member.getAddress())) {
                    throw new Exception("이미 사용중인 주소입니다.");
                } else {
                    update.setAddress(member.getAddress());
                }
            }
            if (member.getPhone() != null && !member.getPhone().isEmpty()) {
                if (phoneDuplicationCheck(id, member.getPhone())) {
                    throw new Exception("이미 사용중인 전화번호입니다.");
                } else {
                    update.setPhone(member.getPhone());
                }
            }
            memberRepository.save(update);
        } else {
            throw new NoSuchElementException("이 아이디를 가진 유저가 없습니다 " + id);
        }
    }

}
