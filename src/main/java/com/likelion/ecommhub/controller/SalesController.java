package com.likelion.ecommhub.controller;

import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.likelion.ecommhub.dto.SalesDto;
import com.likelion.ecommhub.service.SalesService;

@Controller
@RequiredArgsConstructor
public class SalesController {

	private final SalesService salesService;

	@GetMapping("/monthly-sales/{year}")
	public String getMonthlySalesByYear(@PathVariable int year, Model model) {
		List<SalesDto> salesData = salesService.getMonthlySalesByYear(year);
		model.addAttribute("salesData", salesData);
		model.addAttribute("year", year);
		return "monthly-sales";
	}
	@GetMapping("/myPage")
	public String getMonthlySales(Model model) {
		List<SalesDto> salesData = salesService.getMonthlySalesByYear(2023);
		model.addAttribute("salesData", salesData);
		return "usr/member/memberPage";
	}

}