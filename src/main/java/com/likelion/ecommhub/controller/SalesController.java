package com.likelion.ecommhub.controller;

import java.util.Arrays;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.likelion.ecommhub.dto.SalesDto;
import com.likelion.ecommhub.service.SalesService;

@Controller
public class SalesController {

	private final SalesService salesService;

	public SalesController(SalesService salesService) {
		this.salesService = salesService;
	}

	//@GetMapping("/monthly-sales/{year}")
	//public String getMonthlySalesByYear(@PathVariable int year, Model model) {
	//	List<SalesDto> salesList = salesService.getMonthlySalesByYear(year);
	//	model.addAttribute("salesData", salesList);
	//	model.addAttribute("year", year);
	//	return "monthly-sales";

	@GetMapping("/monthly-sales")
	public String getMonthlySales(Model model) {
		//예시로 고정된 데이터를 사용
		List<Integer> salesData = Arrays.asList(1000, 2000, 1500, 2500, 1800, 2200, 1900, 3000, 2800, 3500, 3200, 4000);
		model.addAttribute("salesData", salesData);
		return "monthly-sales";
	}
}