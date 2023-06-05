package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
