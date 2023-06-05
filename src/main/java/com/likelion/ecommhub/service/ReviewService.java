package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.domain.Review;
import com.likelion.ecommhub.dto.ReviewDto;
import com.likelion.ecommhub.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Review postReview(ReviewDto reviewDto, Member member, Product product) {
        // TODO: 주문 테이블 검증로직 필요

        Review createdReview = Review.builder()
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .build();
        createdReview.setMember(member);
        createdReview.setProduct(product);
        reviewRepository.save(createdReview);

        return createdReview;
    }

    @Transactional
    public void deleteReview(Long reviewId, Member member) {
        // TODO: 주문 테이블 검증로직 필요

        reviewRepository.deleteById(reviewId);
    }
}
