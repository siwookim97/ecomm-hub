package com.likelion.ecommhub.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.likelion.ecommhub.domain.Sales;
import com.likelion.ecommhub.dto.SalesDto;
import com.likelion.ecommhub.repository.SalesRepository;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class SalesService {

	private final SalesRepository salesRepository;

	public SalesService(SalesRepository salesRepository) {
		this.salesRepository = salesRepository;
	}

	public List<Map<String, Object>> getMonthlySalesByYear(int year) {
		List<Sales> salesList = salesRepository.findBySaleYear(year); // 해당 연도의 데이터 가져오기

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