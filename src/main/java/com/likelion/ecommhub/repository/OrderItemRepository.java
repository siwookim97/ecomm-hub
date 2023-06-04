package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}