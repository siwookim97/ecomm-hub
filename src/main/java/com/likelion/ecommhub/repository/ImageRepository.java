package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
