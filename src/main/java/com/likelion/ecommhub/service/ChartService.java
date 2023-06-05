package com.likelion.ecommhub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.likelion.ecommhub.domain.MonthlySales;
import com.likelion.ecommhub.repository.MonthlySalesRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// ...

@Service
public class ChartService {
	@Autowired
	MonthlySalesRepository monthlySalesRepository;

	public List<MonthlySales> getMonthlySales(String year) {
		List<MonthlySales> monthlySalesList = new ArrayList<>();

		// Generate dummy data for each month of the specified year
		for (int month = 1; month <= 12; month++) {
			String date = LocalDate.of(Integer.parseInt(year), month, 1).toString();
			long sales = generateRandomSales();
			MonthlySales monthlySales = MonthlySales.builder()
				.date(date)
				.sales(sales)
				.build();
			monthlySalesList.add(monthlySales);
		}

		// Save the generated data to the database
		monthlySalesRepository.saveAll(monthlySalesList);

		return monthlySalesList;
	}
	private long generateRandomSales() {
		Random random = new Random();
		return random.nextInt(9001) + 1000;
	}
}
