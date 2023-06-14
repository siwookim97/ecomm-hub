package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.config.auth.MemberDetails;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.dto.MemberJoinDto;
import com.likelion.ecommhub.dto.MemberLoginDto;
import com.likelion.ecommhub.dto.MemberModifyDto;
import com.likelion.ecommhub.service.MemberService;
import com.likelion.ecommhub.util.Rq;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usr/member")
public class MemberController {

    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("memberJoinDto", new MemberJoinDto());

        return "usr/member/join";
    }

    @GetMapping("/join/seller")
    public String joinSeller(Model model) {
        model.addAttribute("memberJoinDto", new MemberJoinDto());

        return "usr/member/joinSeller";
    }

    @GetMapping("/join/buyer")
    public String joinBuyer(Model model) {
        model.addAttribute("memberJoinDto", new MemberJoinDto());

        return "usr/member/joinBuyer";
    }

    @PostMapping("/join/seller")
    public String joinSeller(@ModelAttribute @Valid MemberJoinDto memberJoinDto) {
        memberService.joinSeller(memberJoinDto);

        return "redirect:/usr/member/login";
    }

    @PostMapping("/join/buyer")
    public String joinBuyer(@ModelAttribute @Valid MemberJoinDto memberJoinDto) {
        memberService.joinBuyer(memberJoinDto);

        return "redirect:/usr/member/login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("memberLoginDto", new MemberLoginDto());

        return "usr/member/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myPage")
    public String myPage(Model model, @AuthenticationPrincipal MemberDetails memberDetails) {
        Member findMember = memberService.getMemberById(memberDetails.getMember().getId());
        model.addAttribute("member", findMember);

        return "usr/member/memberPage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String memberModify(Model model,
                               @AuthenticationPrincipal MemberDetails memberDetails) {

        Member findMember = memberService.getMemberById(memberDetails.getMember().getId());
        model.addAttribute("member", findMember);
        model.addAttribute("memberModifyDto",new MemberModifyDto());
        return "usr/member/memberModify";
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update")
    public String userUpdate(@AuthenticationPrincipal MemberDetails memberDetails,
                             @ModelAttribute @Valid MemberModifyDto memberModifyDto) throws Exception {

        memberService.memberModify(memberDetails.getMember().getId(), memberModifyDto);

        return "redirect:/usr/member/myPage";
    }

    @GetMapping("/seller/{id}")
    public String memberInfo(@PathVariable Long id, Model model) {
        Member findMember = memberService.getMemberById(id);
        model.addAttribute("member", findMember);

        return "usr/member/memberPage";
    }
}
