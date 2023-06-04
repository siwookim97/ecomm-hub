package com.likelion.ecommhub.dto;

import com.likelion.ecommhub.domain.Order;
import com.likelion.ecommhub.domain.OrderStatus;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class OrderListDto {

    private Long orderId;   // 주문 취소에 이용됨
    private String orderDate;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderItemDtoList(List<OrderItemDto> orderItemDtoList) {
        this.orderItemDtoList = orderItemDtoList;
    }

    public OrderListDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    public void addOrderItemDto(OrderItemDto orderItemDto) {
        orderItemDtoList.add(orderItemDto);
    }
}