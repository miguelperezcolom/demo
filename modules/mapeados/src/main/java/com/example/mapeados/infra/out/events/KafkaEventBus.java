package com.example.mapeados.infra.out.events;

import com.example.mapeados.application.out.EventBus;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KafkaEventBus implements EventBus {

    private final StreamBridge streamBridge;
    private static final String BINDING_NAME = "miTopicoSalida";

    @Override
    public Mono<?> send(Object message) {
        return Mono.fromRunnable(() -> streamBridge.send(BINDING_NAME, message));
    }
}
