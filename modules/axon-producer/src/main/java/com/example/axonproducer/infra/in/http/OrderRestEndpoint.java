package com.example.axonproducer.infra.in.http;

import com.example.axonproducer.domain.commands.ConfirmOrderCommand;
import com.example.axonproducer.domain.commands.CreateOrderCommand;
import com.example.axonproducer.domain.commands.ShipOrderCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class OrderRestEndpoint {

    private final CommandGateway commandGateway;

    @PostMapping("/ship-order")
    public CompletableFuture<Void> shipOrder() {
        var orderId = UUID.randomUUID().toString();
        return commandGateway
                .send(new CreateOrderCommand(orderId, "Deluxe Chair"))
                .thenCompose(result -> commandGateway.send(new ConfirmOrderCommand(orderId)))
                .thenCompose(result -> commandGateway.send(new ShipOrderCommand(orderId)))
                ;
    }
}
