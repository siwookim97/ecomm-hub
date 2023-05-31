package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.dto.MemberJoinDto;
import com.likelion.ecommhub.dto.MemberLoginDto;
import com.likelion.ecommhub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/joinForm")
    public String join(Model model) {
        model.addAttribute("memberJoinDto", new MemberJoinDto());

        return "member/join";
    }

    @PostMapping("/joinSeller")
    public String joinSeller(@ModelAttribute() @Valid MemberJoinDto memberJoinDto) {

        String result = memberService.joinSeller(memberJoinDto);
        System.out.println("result = " + result);

        return "redirect:/member/loginForm";
    }

    @PostMapping("/joinBuyer")
    public String joinBuyer(@ModelAttribute("memberJoinDto") @Valid MemberJoinDto memberJoinDto) {

        String result = memberService.joinBuyer(memberJoinDto);
        System.out.println("result = " + result);

        return "redirect:/member/loginForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        model.addAttribute("memberLoginDto", new MemberLoginDto());

        return "member/login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccessTest() {

        return "member/loginSuccess";
    }
}
