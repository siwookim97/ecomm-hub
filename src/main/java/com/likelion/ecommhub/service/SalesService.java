package com.likelion.ecommhub.service;

import java.math.BigDecimal;
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

		Map<Integer, BigDecimal> monthlySalesMap = new HashMap<>();

		for (Sales sales : salesList) {
			int month = sales.getSaleMonth();
			BigDecimal salesAmount = sales.getSales();

			if (monthlySalesMap.containsKey(month)) {
				BigDecimal currentSalesAmount = monthlySalesMap.get(month);
				monthlySalesMap.put(month, currentSalesAmount.add(salesAmount));
			} else {
				monthlySalesMap.put(month, salesAmount);
			}
		}

		List<Map<String, Object>> salesData = new ArrayList<>();
		for (Map.Entry<Integer, BigDecimal> entry : monthlySalesMap.entrySet()) {
			int salesMonth = entry.getKey();
			BigDecimal salesAmount = entry.getValue();

			Map<String, Object> salesMap = new HashMap<>();
			salesMap.put("salesMonth", salesMonth);
			salesMap.put("sales", salesAmount);
			salesData.add(salesMap);
		}

		return salesData;
	}
}