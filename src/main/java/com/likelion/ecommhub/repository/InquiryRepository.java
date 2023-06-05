package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
