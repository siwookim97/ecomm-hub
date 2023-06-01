package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
