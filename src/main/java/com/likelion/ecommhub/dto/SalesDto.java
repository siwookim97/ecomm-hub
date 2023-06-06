package com.likelion.ecommhub.dto;

import java.math.BigDecimal;


import lombok.Setter;


public class SalesDto {

	private BigDecimal sales;

	public void setSales(BigDecimal sales) {
		this.sales =sales;
	}
}