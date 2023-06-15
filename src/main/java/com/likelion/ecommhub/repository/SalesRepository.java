package com.likelion.ecommhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Sales;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {

	Sales findByMember(Member Seller);
	List<Sales> findBySaleYearAndMemberId(int year, long memberId);
	List<Sales> findByProductId(Long productId);
}