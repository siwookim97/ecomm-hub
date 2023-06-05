package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Inquiry;
import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.dto.InquiryDto;
import com.likelion.ecommhub.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    @Transactional
    public Inquiry postInquiry(InquiryDto inquiryDto, Member member, Product product) {
        Inquiry createdInquiry = Inquiry.builder()
                .title(inquiryDto.getTitle())
                .content(inquiryDto.getContent())
                .build();
        createdInquiry.setMember(member);
        createdInquiry.setProduct(product);
        inquiryRepository.save(createdInquiry);

        return createdInquiry;
    }

    @Transactional
    public void deleteInquiry(Long inquiryId, Member member) {
        Inquiry findInquiry = inquiryRepository.findById(inquiryId)
                .orElseGet(() -> Inquiry.builder().title(null).content(null).build());

        if (member.getId().equals(findInquiry.getMember().getId())) {
            System.out.println("AUTHENTICATION FAIL");
        }

        inquiryRepository.deleteById(inquiryId);
    }
}
