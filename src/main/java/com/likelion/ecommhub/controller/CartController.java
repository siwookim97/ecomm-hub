package com.likelion.ecommhub.controller;


import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.dto.CartDto;
import com.likelion.ecommhub.service.CartService;
import com.likelion.ecommhub.service.MemberService;

@Controller
@RequestMapping("/cart")
public class CartController {

	private final CartService cartService;
	private final MemberService memberService;

	public CartController(CartService cartService, MemberService memberService) {
		this.cartService = cartService;
		this.memberService = memberService;
	}

	@GetMapping("/create")
	public String createCart(HttpSession session) {
		// 세션에서 구매자 아이디 조회
		Long memberId = (Long) session.getAttribute("memberId");

		// 구매자 정보 조회
		Optional<Member> member = memberService.getMemberId(memberId);

		// 장바구니 생성
		CartDto cartDto = new CartDto();
		cartDto.setMemberId(memberId);
		cartDto.setMemberName(memberDto.getName());

		// 장바구니 저장
		cartService.createCart(cartDto);

		// 장바구니 페이지로 리다이렉트
		return "redirect:/cart/view?cartId=" + cartDto.getCartId();
	}

	@GetMapping("/view")
	public String viewCart(@RequestParam("cartId") Long cartId, Model model) {
		// 장바구니 정보 조회
		CartDto cartDto = cartService.getCartById(cartId);

		// 모델에 데이터 전달
		model.addAttribute("cart", cartDto);

		// 장바구니 페이지로 이동
		return "cart";
	}

	// 이하 생략
}

