package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.dto.MemberJoinDto;
import com.likelion.ecommhub.dto.MemberLoginDto;
import com.likelion.ecommhub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

    @PostMapping("/login")
    public String login(HttpSession session, @RequestBody @Valid MemberLoginDto memberLoginDto) {
        String token = memberService.login(memberLoginDto.getLoginId(), memberLoginDto.getPassword());
        System.out.println("token = " + token);
        session.setAttribute("token", token);

        return "member/loginResult";
    }
}
