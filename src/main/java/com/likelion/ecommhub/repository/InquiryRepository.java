package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    List<Inquiry> findByProductId(Long productId);
}
