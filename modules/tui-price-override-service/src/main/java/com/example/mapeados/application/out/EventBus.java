package com.example.mapeados.application.out;

import reactor.core.publisher.Mono;

public interface EventBus {

    Mono<?> send(Object message);

}
