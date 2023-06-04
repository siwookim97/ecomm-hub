package com.likelion.ecommhub.dto;

import com.likelion.ecommhub.domain.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemDto {

    private String itemName;
    private int count;
    private int orderPrice;

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }


    public OrderItemDto(OrderItem orderItem) {
        this.itemName = orderItem.getProduct().getName();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
    }
}