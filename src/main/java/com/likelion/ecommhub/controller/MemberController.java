package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.config.auth.MemberDetails;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.MemberRole;
import com.likelion.ecommhub.dto.MemberJoinDto;
import com.likelion.ecommhub.dto.MemberLoginDto;
import com.likelion.ecommhub.service.MemberService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usr/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/joinForm")
    public String join(Model model) {
        model.addAttribute("memberJoinDto", new MemberJoinDto());

        return "usr/member/join";
    }

    @PostMapping("/joinSeller")
    public String joinSeller(@ModelAttribute() @Valid MemberJoinDto memberJoinDto) {

        String result = memberService.joinSeller(memberJoinDto);

        return "redirect:/usr/member/loginForm";
    }

    @PostMapping("/joinBuyer")
    public String joinBuyer(@ModelAttribute("memberJoinDto") @Valid MemberJoinDto memberJoinDto) {

        String result = memberService.joinBuyer(memberJoinDto);

        return "redirect:/usr/member/loginForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        model.addAttribute("memberLoginDto", new MemberLoginDto());

        return "usr/member/login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccessTest() {

        return "usr/member/loginSuccess";
    }

    @GetMapping("/myPage")
    public String userPage(Model model,
        @AuthenticationPrincipal MemberDetails memberDetails) {


            model.addAttribute("user", memberDetails.getMember());

            return "usr/member/memberPage";
        }


    @GetMapping("/modify/{id}")
    public String memberModify(@PathVariable("id") Long id, Model model,
        @AuthenticationPrincipal MemberDetails memberDetails) {
        if (memberDetails.getMember().getId().equals(id)) {

            model.addAttribute("user", memberService.getMemberId(id));

            return "usr/member/memberModify";
        } else {
            return "redirect:/main";
        }

    }

    @PutMapping("/update/{id}")
    public String userUpdate(@PathVariable("id") Long id, Member member,
        @AuthenticationPrincipal MemberDetails memberDetails) throws Exception {
        if (memberDetails.getMember().getId().equals(id)) {
            memberService.memberModify(id, member);
        }
        return "redirect:/usr/member/{id}";
    }

}
