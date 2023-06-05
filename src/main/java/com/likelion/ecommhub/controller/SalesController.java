package com.likelion.ecommhub.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.likelion.ecommhub.dto.SalesDto;
import com.likelion.ecommhub.service.SalesService;

@RestController
@RequestMapping("/monthly-sales")
public class SalesController {

	private final SalesService salesService;

	public SalesController(SalesService salesService) {
		this.salesService = salesService;
	}

	@GetMapping("/{year}")
	public ResponseEntity<List<SalesDto>> getMonthlySalesByYear(@PathVariable int year) {
		List<SalesDto> salesDTOList = salesService.getMonthlySalesByYear(year);
		return ResponseEntity.ok(salesDTOList);
	}
}