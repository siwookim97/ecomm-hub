package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Member;
import com.likelion.ecommhub.domain.Order;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.domain.Review;
import com.likelion.ecommhub.dto.ReviewDto;
import com.likelion.ecommhub.repository.MemberRepository;
import com.likelion.ecommhub.repository.ProductRepository;
import com.likelion.ecommhub.repository.ReviewRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void postReview(ReviewDto reviewDto, Long memberId, Long productId) {
        Member findMember = findMember(memberId);
        Product findProduct = findProduct(productId);
        Order findMemberOrder = findMember.getOrders().stream()
                .filter(order -> order.getMember().equals(findMember))
                .findFirst()
                .orElse(null);

        Review createdReview = Review.builder()
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .build();
        createdReview.setMember(findMember);
        createdReview.setProduct(findProduct);

        if (findMemberOrder != null) {
            reviewRepository.save(createdReview);
        }
    }

    @Transactional
    public void deleteReview(Long reviewId, Long memberId) {
        Review findReview = findReview(reviewId);
        Member findMember = findMember(memberId);

        if (findReview.getMember().getId().equals(findMember.getId())) {
            reviewRepository.delete(findReview);
        }
    }

    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("no such MEMBER"));
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("no such PRODUCT"));
    }

    private Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("no such REVIEW"));
    }
}
