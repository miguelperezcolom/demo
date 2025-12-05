package com.example.axonreadmodel.infra.in.http;

import com.example.axonreadmodel.application.FindAllOrderedProductQuery;
import com.example.axonreadmodel.domain.Order;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class OrderRestEndpoint {

    private final QueryGateway queryGateway;

    @GetMapping("/all-orders")
    public CompletableFuture<List<Order>> findAllOrders() {
        return queryGateway.query(new FindAllOrderedProductQuery(),
                ResponseTypes.multipleInstancesOf(Order.class));
    }
}
