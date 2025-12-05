package com.example.shared.events;

import lombok.Data;

@Data
public class OrderConfirmedEvent {

    private final String orderId;

}
