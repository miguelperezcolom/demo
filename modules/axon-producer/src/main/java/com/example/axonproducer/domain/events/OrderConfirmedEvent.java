package com.example.axonproducer.domain.events;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class OrderConfirmedEvent {

    private final String orderId;

}
