package com.example.axonproducer.domain.aggregates;

import com.example.axonproducer.domain.commands.ConfirmOrderCommand;
import com.example.axonproducer.domain.commands.CreateOrderCommand;
import com.example.axonproducer.domain.commands.ShipOrderCommand;
import com.example.shared.events.OrderConfirmedEvent;
import com.example.shared.events.OrderCreatedEvent;
import com.example.shared.events.OrderShippedEvent;
import com.example.axonproducer.domain.exceptions.UnconfirmedOrderException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private boolean orderConfirmed;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        apply(new OrderCreatedEvent(command.getOrderId(), command.getProductId()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        orderConfirmed = false;
    }

    protected OrderAggregate() {

    }



    @CommandHandler
    public void handle(ConfirmOrderCommand command) {
        if (orderConfirmed) {
            return;
        }
        apply(new OrderConfirmedEvent(command.getOrderId()));
    }

    @CommandHandler
    public void handle(ShipOrderCommand command) {
        if (!orderConfirmed) {
            throw new UnconfirmedOrderException();
        }
        apply(new OrderShippedEvent(command.getOrderId()));
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent command) {
        orderConfirmed = true;
    }

}
