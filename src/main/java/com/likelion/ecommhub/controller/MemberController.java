package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.config.auth.MemberDetails;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.MemberRole;
import com.likelion.ecommhub.dto.MemberJoinDto;
import com.likelion.ecommhub.dto.MemberLoginDto;
import com.likelion.ecommhub.service.MemberService;
import com.likelion.ecommhub.util.Rq;
import com.likelion.ecommhub.util.RsData;
import com.likelion.ecommhub.util.Ut;

import lombok.RequiredArgsConstructor;


import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private final Rq rq;


    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoinForm(Model model) {
        model.addAttribute("memberJoinDto", new MemberJoinDto());
        return "usr/member/join";
    }


    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@ModelAttribute("memberJoinDto") @Valid MemberJoinDto memberJoinDto) {

        RsData<Member> joinRs = memberService.join(memberJoinDto);

        if(joinRs.isFail()){
            return rq.historyBack(joinRs);
        }

        return rq.redirectWithMsg("/usr/member/loginForm", joinRs);
    }

    @PostMapping("/joinBuyer")
    public String joinBuyer(@ModelAttribute("memberJoinDto") @Valid MemberJoinDto memberJoinDto) {


        RsData<Member> joinRs = memberService.join(memberJoinDto);

        if(joinRs.isFail()){
            return rq.historyBack(joinRs);
        }

        return rq.redirectWithMsg("/usr/member/loginForm", joinRs);
    }
    @PreAuthorize("isAnonymous()")
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
