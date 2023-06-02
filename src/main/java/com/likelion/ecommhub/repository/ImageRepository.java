package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findImagesByProductId(Long product_id);
}
