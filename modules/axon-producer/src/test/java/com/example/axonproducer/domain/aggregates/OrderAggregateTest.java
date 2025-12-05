package com.example.axonproducer.domain.aggregates;

import com.example.axonproducer.domain.commands.CreateOrderCommand;
import com.example.axonproducer.domain.commands.ShipOrderCommand;
import com.example.shared.events.OrderConfirmedEvent;
import com.example.shared.events.OrderCreatedEvent;
import com.example.shared.events.OrderShippedEvent;
import com.example.axonproducer.domain.exceptions.UnconfirmedOrderException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderAggregateTest {

    private FixtureConfiguration<OrderAggregate> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(OrderAggregate.class);
    }

    @Test
    void orderIsCreated() {
        var orderId = UUID.randomUUID().toString();
        var productId = "Deluxe Chair";
        fixture.givenNoPriorActivity()
                .when(new CreateOrderCommand(orderId, productId))
                .expectEvents(new OrderCreatedEvent(orderId, productId));
    }

    @Test
    void exceptionIsThrown() {
        var orderId = UUID.randomUUID().toString();
        var productId = "Deluxe Chair";
        fixture.given(new OrderCreatedEvent(orderId, productId))
                .when(new ShipOrderCommand(orderId))
                .expectException(UnconfirmedOrderException.class);
    }

    @Test
    void orderIsShipped() {
        var orderId = UUID.randomUUID().toString();
        var productId = "Deluxe Chair";
        fixture.given(new OrderCreatedEvent(orderId, productId), new OrderConfirmedEvent(orderId))
                .when(new ShipOrderCommand(orderId))
                .expectEvents(new OrderShippedEvent(orderId));

    }


}