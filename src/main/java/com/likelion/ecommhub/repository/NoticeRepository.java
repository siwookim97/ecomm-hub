package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Optional<Notice> findByTitle(String title);
}