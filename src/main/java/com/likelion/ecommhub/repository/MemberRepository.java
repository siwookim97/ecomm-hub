package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByMemberRole(MemberRole memberRole);
    Optional<Member> findByUsername(String username);
    Member findByNickname( String nickname);
}