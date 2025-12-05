package com.example.axonproducer.domain.events;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class OrderCreatedEvent {

    private final String orderId;
    private final String productId;

}
