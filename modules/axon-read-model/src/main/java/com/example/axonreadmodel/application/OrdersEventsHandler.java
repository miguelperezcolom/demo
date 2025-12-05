package com.example.axonreadmodel.application;

import com.example.axonreadmodel.domain.Order;
import com.example.axonreadmodel.domain.OrderStatus;
import com.example.shared.events.OrderCreatedEvent;
import com.example.shared.events.OrderShippedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersEventsHandler {

    private final Map<String, Order> orders = new HashMap<>();

    @EventHandler
    public void on(OrderCreatedEvent event) {
        var orderId = event.getOrderId();
        orders.put(orderId, Order.builder()
                        .orderId(orderId)
                        .productId(event.getProductId())
                        .orderStatus(OrderStatus.CREATED)
                .build());
    }

    @QueryHandler
    public List<Order> handle(FindAllOrderedProductQuery query) {
        return new ArrayList<>(orders.values());
    }

}
