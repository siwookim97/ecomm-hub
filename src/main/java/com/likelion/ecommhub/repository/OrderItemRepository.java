package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findOrderItemsByMemberId(Long memberId);

    List<OrderItem> findAll();

    OrderItem findOrderItemById(Long orderItemId);
}