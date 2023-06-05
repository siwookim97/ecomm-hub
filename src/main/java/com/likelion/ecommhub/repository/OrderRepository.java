package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByMemberId(Long id);
}