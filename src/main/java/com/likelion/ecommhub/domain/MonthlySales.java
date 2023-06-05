package com.likelion.ecommhub.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;



@Entity
public class MonthlySales {
	@Id
	private String date;
	private long sales;

	@Builder //해당 클래스의 빌더패턴 클래스를 생성
	public MonthlySales(String date, long sales) {
		this.date = date;
		this.sales = sales;
	}
	@Builder
	public MonthlySales() {

	}
}
