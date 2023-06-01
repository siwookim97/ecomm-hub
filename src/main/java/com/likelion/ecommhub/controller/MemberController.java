package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.dto.MemberJoinDto;
import com.likelion.ecommhub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/joinSeller")
    public String joinSeller(@RequestBody @Valid MemberJoinDto memberJoinDto) {
        String result = memberService.joinSeller(memberJoinDto);
        System.out.println("result = " + result);

        return "member/joinSeller";
    }

    @PostMapping("/joinBuyer")
    public String joinBuyer(@RequestBody @Valid MemberJoinDto memberJoinDto) {
        String result = memberService.joinBuyer(memberJoinDto);
        System.out.println("result = " + result);

        return "member/joinBuyer";
    }
}
