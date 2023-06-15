package com.likelion.ecommhub.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import com.likelion.ecommhub.domain.Sales;
import com.likelion.ecommhub.repository.SalesRepository;

import java.util.ArrayList;
import java.util.Map;
@Service
public class SalesService {

	private final SalesRepository salesRepository;

	public SalesService(SalesRepository salesRepository) {
		this.salesRepository = salesRepository;
	}

	public List<Map<String, Object>> getMonthlySalesByYearAndMemberId(int year, long memberId) {
		List<Sales> salesList = salesRepository.findBySaleYearAndMemberId(year, memberId);

		List<Map<String, Object>> salesData = new ArrayList<>();
		for (Sales sales : salesList) {
			Map<String, Object> salesMap = new HashMap<>();
			salesMap.put("salesMonth", sales.getSaleMonth());
			salesMap.put("sales", sales.getSales());
			salesData.add(salesMap);
		}
		return salesData;
	}
}