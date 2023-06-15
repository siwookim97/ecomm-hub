package com.likelion.ecommhub.controller;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.likelion.ecommhub.config.auth.MemberDetails;
import com.likelion.ecommhub.service.SalesService;

@Controller
@RequiredArgsConstructor
public class SalesController {

	private final SalesService salesService;
	@PreAuthorize("hasRole('ROLE_SELLER')")
	@GetMapping("/usr/member/monthly-sales/{year}")
	public String getMonthlySalesByYear(@PathVariable int year, Model model, @AuthenticationPrincipal MemberDetails memberDetails) {
		Long memberId = memberDetails.getMember().getId();

		List<Map<String, Object>> salesData = salesService.getMonthlySalesByYearAndMemberId(year, memberId);
		model.addAttribute("salesData", salesData);
		model.addAttribute("year", year);
		return "usr/member/monthly-sales";
	}

}
