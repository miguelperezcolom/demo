package com.example.axonreadmodel.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {

    private String orderId;
    private String productId;
    private OrderStatus orderStatus;

    public void setOrderConfirmed() {
        this.orderStatus = OrderStatus.CONFIRMED;
    }

    public void setOrderShipped() {
        this.orderStatus = OrderStatus.SHIPPED;
    }

}
