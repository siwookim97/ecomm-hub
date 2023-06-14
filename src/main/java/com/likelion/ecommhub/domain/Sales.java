package com.likelion.ecommhub.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sales {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(nullable = false)
	private BigDecimal sales; //매출액

	@Column(nullable = false)
	private LocalDateTime saleDate;

	@Column(nullable = false)
	private int saleYear; // 1년 동안의 매출액

	@Builder
	public Sales(Product product, Order order, BigDecimal sales, LocalDateTime saleDate, int saleYear, Member member) {
		this.product = product;
		this.order = order;
		this.sales = sales;
		this.saleDate = saleDate;
		this.saleYear = saleYear;
		this.member = member;
	}
}