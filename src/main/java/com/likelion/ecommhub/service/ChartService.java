package com.likelion.ecommhub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.likelion.ecommhub.domain.MonthlySales;
import com.likelion.ecommhub.repository.MonthlySalesRepository;

@Service
public class ChartService {
	@Autowired
	MonthlySalesRepository monthlySalesRepository;

	/**
	 * 월별 매출액 조회
	 * @param year
	 * @return
	 */
	public List<MonthlySales> getMonthlySales(String year){
		return monthlySalesRepository.findByDateStartsWith(year);
	}
}