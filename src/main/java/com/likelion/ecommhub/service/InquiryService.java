package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Inquiry;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.dto.InquiryDto;
import com.likelion.ecommhub.repository.InquiryRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    @Transactional
    public Inquiry postInquiry(InquiryDto inquiryDto, Member member, Product product) {
        // TODO: 주문 테이블 검증로직 필요

        Inquiry createdInquiry = Inquiry.builder()
                .title(inquiryDto.getTitle())
                .content(inquiryDto.getContent())
                .build();
        createdInquiry.setMember(member);
        createdInquiry.setProduct(product);
        inquiryRepository.save(createdInquiry);

        return createdInquiry;
    }
}
