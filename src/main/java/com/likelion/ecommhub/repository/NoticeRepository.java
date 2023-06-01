package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
}
