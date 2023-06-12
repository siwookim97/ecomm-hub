package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.config.auth.MemberDetails;
import com.likelion.ecommhub.domain.Member;
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

    @GetMapping("/join")
    public String join() {
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
    public String joinSeller(@ModelAttribute @Valid MemberJoinDto memberJoinDto) {
        memberService.joinSeller(memberJoinDto);

        return "redirect:/usr/member/login";
    }

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

    @PostMapping("/join/buyer")
    public String joinBuyer(@ModelAttribute("memberJoinDto") @Valid MemberJoinDto memberJoinDto) {
        memberService.joinBuyer(memberJoinDto);

        return "redirect:/usr/member/login";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("memberJoinDto") @Valid MemberJoinDto memberJoinDto, Model model) {
        RsData<Member> joinRs = memberService.join(memberJoinDto);
        if(joinRs.isFail()){
            return rq.historyBack(joinRs);
        }

        return rq.redirectWithMsg("/usr/member/loginForm", joinRs);
    }
  
    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
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
  
    @GetMapping("/seller/{id}")
    public String memberInfo(@PathVariable("id") Long id, Model model) {
        Member member = memberService.getMemberId(id).orElseThrow();
        model.addAttribute("user", member);
        return "usr/member/memberPage";
    }

}
