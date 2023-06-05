package com.likelion.ecommhub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.likelion.ecommhub.domain.MonthlySales;
import com.likelion.ecommhub.service.ChartService;

@RestController
public class ChartController {
	@Autowired
	ChartService chartService;

	@GetMapping("/monthly-sales/{year}")
	public List<MonthlySales> monthlySalesList(@PathVariable("year") String year){
		return chartService.getMonthlySales(year);
	}
}