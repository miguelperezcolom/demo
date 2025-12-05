package com.example.axonproducer.domain.events;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class OrderShippedEvent {

    private final String orderId;

}
