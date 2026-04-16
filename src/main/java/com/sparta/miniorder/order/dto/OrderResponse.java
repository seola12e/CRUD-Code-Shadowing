package com.sparta.miniorder.order.dto;

import com.sparta.miniorder.order.entity.Order;
import lombok.Getter;

@Getter
public class OrderResponse {

    private final Long orderId;
    /**
     * Product 관련 반환은 productId, productName, productPrice 로 한다.
     */
    private final Long productId;
    private final String productName;
    private final Integer productPrice;

    public OrderResponse(Order saved) {
        this.orderId = saved.getId();
        this.productId = saved.getProduct().getId();
        this.productName = saved.getProduct().getName();
        this.productPrice = saved.getProduct().getPrice();
    }
}
