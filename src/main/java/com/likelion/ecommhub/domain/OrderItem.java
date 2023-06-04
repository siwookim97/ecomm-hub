package com.likelion.ecommhub.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_product")
@Getter
@Setter
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문가격
    private int count; //수량

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static OrderItem createOrderItem(Product product, int count) {

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setCount(count);
        orderItem.setOrderPrice(product.getPrice());

        product.removeInventory(count);
        return orderItem;
    }

    public int getTotalPrice() {
        return this.orderPrice * this.count;
    }

    public void cancel() {
        this.getProduct().addInventory(count);
    }
}
