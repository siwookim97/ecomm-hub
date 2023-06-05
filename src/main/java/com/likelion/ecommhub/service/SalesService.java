package com.likelion.ecommhub.service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.likelion.ecommhub.domain.Sales;
import com.likelion.ecommhub.dto.SalesDto;
import com.likelion.ecommhub.repository.SalesRepository;


import java.util.ArrayList;

@Service
public class SalesService {

	private final SalesRepository salesRepository;

	public SalesService(SalesRepository salesRepository) {
		this.salesRepository = salesRepository;
	}

	public List<SalesDto> getMonthlySalesByYear(int year) {
		List<Sales> salesList = salesRepository.findBySaleYear(year);
		return convertToDTOList(salesList);
	}

	private List<SalesDto> convertToDTOList(List<Sales> salesList) {
		List<SalesDto> salesDTOList = new ArrayList<>();
		for (Sales sales : salesList) {
			SalesDto salesDTO = new SalesDto();
			salesDTO.setSales(sales.getSales());
			salesDTOList.add(salesDTO);
		}
		return salesDTOList;
	}
}