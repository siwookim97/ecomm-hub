package com.likelion.ecommhub.controller;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.likelion.ecommhub.service.SalesService;

@Controller
@RequiredArgsConstructor
public class SalesController {

	private final SalesService salesService;

	@GetMapping("/usr/member/monthly-sales/{year}")
	public String getMonthlySalesByYear(@PathVariable int year, Model model) {
		List<Map<String, Object>> salesData = salesService.getMonthlySalesByYear(year);
		model.addAttribute("salesData", salesData);
		model.addAttribute("year", year);
		return "usr/member/monthly-sales";
	}

	@GetMapping("/myPage")
	public String getMonthlySales(Model model) {
		int year = 2023;
		List<Map<String, Object>> salesData = salesService.getMonthlySalesByYear(year);
		model.addAttribute("salesData", salesData);
		model.addAttribute("year", year);
		return "usr/member/memberPage";
	}
}
